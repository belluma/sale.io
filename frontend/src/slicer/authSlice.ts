import {createAsyncThunk, createSlice, PayloadAction} from "@reduxjs/toolkit";
import {ICredentials} from "../interfaces/IEmployee";
import {RootState} from "../app/store";
import {getErrorMessage} from "./errorSlice";
import {registerAdmin as registerAsAdmin, sendLoginData, validateToken} from "../services/authService";

const initialState = {
    loggedIn: false,
    token: ""
}


export const login = createAsyncThunk(
    'login',
    async (credentials: ICredentials, thunkAPI) => {
        const {data, status, statusText} = await sendLoginData(credentials)
        if (status !== 200) {
            thunkAPI.dispatch(getErrorMessage({status, statusText}))
        }
        return {data, status, statusText}
    }
)
export const registerAdmin = createAsyncThunk(
    'signup',
    async (credentials: ICredentials, thunkAPI) => {
        const {data, status, statusText} = await registerAsAdmin(credentials)
        if (status !== 200) {
            thunkAPI.dispatch(getErrorMessage({status, statusText}))
        }
        return {data, status, statusText}
    }
)

interface IResponseData {
    data: string;
    status: number,
    statusText: string
}

//TODO change token name after deciding app name

export const Authentication = createSlice({
    name: 'login',
    initialState,
    reducers: {
        logout: (state) => {
            localStorage.removeItem("appNameToken");
            state.loggedIn = false;
        },
        loginFromStorage: (state) => {
            const token = localStorage.getItem("appNameToken");
            if (token && validateToken(token)) {
                state.loggedIn = true;
                state.token = token;
            }

        }

    },
    extraReducers: builder => {
        builder
            .addCase(login.pending, state => {
            })
            .addCase(login.fulfilled, (state, action: PayloadAction<IResponseData>) => {
                if (action.payload.status !== 200) {
                    return;
                }
                state.loggedIn = true;
                state.token = action.payload.data
                localStorage.setItem('appNameToken', action.payload.data);
            })
            .addCase(registerAdmin.fulfilled, (state, action: PayloadAction<IResponseData>) => {
                if (action.payload.status !== 200) {
                    return;
                }
                state.loggedIn = true;
                state.token = action.payload.data
                localStorage.setItem('appNameToken', action.payload.data);
            })
    }
})

export const {logout, loginFromStorage} = Authentication.actions;

export const selectLoggedIn = (state: RootState) => state.authentication.loggedIn;
export const selectToken = (state: RootState) => state.authentication.token;
export default Authentication.reducer;
