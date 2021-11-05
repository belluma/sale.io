import {createAsyncThunk, createSlice, PayloadAction} from "@reduxjs/toolkit";
import {ICredentials} from "../interfaces/IEmployee";
import {RootState} from "../app/store";
import {getErrorMessage} from "./errorSlice";
import {registerAdmin as registerAsAdmin, sendLoginData} from "../services/authService";
import {validateToken} from "../services/jwtService";
import {IResponseData} from "../interfaces/IApiResponse";
import {IAuthState} from "../interfaces/IStates";
import history from "../services/history"

const initialState: IAuthState = {
    loggedIn: false,
    token: ""
}


export const login = createAsyncThunk(
    'login',
    async (credentials: ICredentials, thunkAPI) => {
        const {data, status, statusText} = await sendLoginData(credentials);
        if (status !== 200) {
            thunkAPI.dispatch(getErrorMessage({status, statusText}));
        }
        return {data, status, statusText};
    }
)
export const registerAdmin = createAsyncThunk(
    'signup',
    async (credentials: ICredentials, thunkAPI) => {
        const {data, status, statusText} = await registerAsAdmin(credentials);
        if (status !== 200) {
            thunkAPI.dispatch(getErrorMessage({status, statusText}));
        }
        return {data, status, statusText};
    }
)

export const Authentication = createSlice({
    name: 'login',
    initialState,
    reducers: {
        logout: (state) => {
            state.token = "";
            localStorage.removeItem("SaleioToken");
            state.loggedIn = false;
            history.push('/start')
        },
        loginFromStorage: (state) => {
            const token = localStorage.getItem("SaleioToken");
            if (token && validateToken(token)) {
                state.loggedIn = true;
                state.token = token;
            }

        }

    },
    extraReducers: builder => {
        builder

            .addCase(login.fulfilled, (state, action: PayloadAction<IResponseData>) => {
                if (action.payload.status !== 200) {
                    return;
                }
                state.loggedIn = true;
                state.token = action.payload.data
                localStorage.setItem('SaleioToken', action.payload.data);
            })
            .addCase(registerAdmin.fulfilled, (state, action: PayloadAction<IResponseData>) => {
                if (action.payload.status !== 200) {
                    return;
                }
                state.loggedIn = true;
                state.token = action.payload.data
                localStorage.setItem('SaleioToken', action.payload.data);
            })
    }
})

export const {logout, loginFromStorage} = Authentication.actions;

export const selectLoggedIn = (state: RootState) => state.authentication.loggedIn;
export default Authentication.reducer;
