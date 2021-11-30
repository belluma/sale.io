import {
    Action,
    createAsyncThunk,
    createSlice,
    PayloadAction, ThunkDispatch
} from "@reduxjs/toolkit";
import {IUserCredentials} from "../interfaces/IEmployee";
import {RootState} from "../app/store";
import {registerAdmin as registerAsAdmin, sendLoginData} from "../services/authService";
import {validateToken} from "../services/jwtService";
import {IResponseData} from "../interfaces/IApiResponse";
import {IAuthState} from "../interfaces/IStates";
import history from "../services/history"
import {hideDetails} from "./detailsSlice";
import {handleError} from "./errorHelper";
import {getAllProducts} from "./productSlice";
import {getAllSuppliers} from "./supplierSlice";
import {getAllCategories} from "./categorySlice";
import {getAllOpenCustomers} from "./customerSlice";
import {getAllOrders} from "./orderSlice";

const initialState: IAuthState = {
    loggedIn: false,
    token: ""
}

const fetchApiData = (dispatch: ThunkDispatch<RootState, void, Action>) => {
    dispatch(getAllProducts())
    dispatch(getAllSuppliers())
    dispatch(getAllOrders())
    dispatch(getAllOpenCustomers())
    dispatch(getAllCategories())
}
export const login = createAsyncThunk(
    'login',
    async (credentials: IUserCredentials, {dispatch}) => {
        const {data, status, statusText} = await sendLoginData(credentials);
        dispatch(hideDetails());
        if (status !== 200) {
            handleError(status, statusText, dispatch);
        }else fetchApiData(dispatch)
        return {data, status, statusText};
    }
)
export const loginFromStorage = createAsyncThunk(
    'loginFromStorage',
    (_, { dispatch}) => {
        const token = localStorage.getItem("SaleioToken");
        const data = token || "";
        let status = 403;
        const statusText = ""
        if (token && validateToken(token)) {
            status = 200;
            fetchApiData(dispatch)
        }
        return {data, status, statusText}
    }
)


export const registerAdmin = createAsyncThunk(
    'signup',
    async (credentials: IUserCredentials, {dispatch}) => {
        const {data, status, statusText} = await registerAsAdmin(credentials);
        if (status !== 200) {
            handleError(status, statusText, dispatch);
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
    },
    extraReducers: builder => {
        const handleLogin = (state:IAuthState, action: PayloadAction<IResponseData>) => {
            if (action.payload.status !== 200) {
                return;
            }
            state.loggedIn = true;
            state.token = action.payload.data
            localStorage.setItem('SaleioToken', action.payload.data);
        }
        builder
            .addCase(login.fulfilled, (state, action: PayloadAction<IResponseData>) => {
              handleLogin(state, action)
            })
            .addCase(registerAdmin.fulfilled, (state, action: PayloadAction<IResponseData>) => {
                handleLogin(state, action)
            })
            .addCase(loginFromStorage.fulfilled, (state, action: PayloadAction<IResponseData>) => {
                handleLogin(state, action)
            })

    }
})

export const {logout} = Authentication.actions;

export const selectLoggedIn = (state: RootState) => state.authentication.loggedIn;
export default Authentication.reducer;
