import {createAsyncThunk, createSlice, PayloadAction} from "@reduxjs/toolkit";
import {ICredentials} from "../interfaces/IEmployee";
import {RootState} from "../app/store";
import {getErrorMessage} from "./ErrorSlice";
import {sendLoginDataToGithub, sendLoginData, validateToken} from "../services/authService";
import history from '../services/history'

const initialState = {
    loggedIn: false,
    token:""
}


export const login = createAsyncThunk(
    'login',
    async (credentials:ICredentials, thunkAPI) =>  {
        const {data, status, statusText} = await sendLoginData(credentials)
        if(status !== 200) {
            thunkAPI.dispatch(getErrorMessage({status,statusText}))
        }
        return {data, status, statusText}
    }
)
export const loginWithGithub = createAsyncThunk(
    'githubLogin',
    async (code:string, thunkAPI) =>  {
        console.log(code);
        const {data, status, statusText} = await sendLoginDataToGithub(code)
        if(status !== 200) {
            history.push('/login')
            thunkAPI.dispatch(getErrorMessage({status,statusText}))
        } else history.push('/quiz')
        return {data, status, statusText}
    }
)



interface IResponseData {
    data: string;
    status: number,
    statusText:string
}

export const LoginSlice = createSlice({
    name:'login',
    initialState,
    reducers:{
        logout:(state) => {
            localStorage.removeItem("codificantesToken");
            state.loggedIn = false;
        },
        loginFromStorage:(state) =>{
            const token = localStorage.getItem("codificantesToken");
            if(token && validateToken(token)){
                state.loggedIn = true;
                state.token = token;
            }

        }

    },
    extraReducers:builder => {
        builder
            .addCase(login.pending, state => {
            })
            .addCase(login.fulfilled, (state, action: PayloadAction<IResponseData>) => {
                if (action.payload.status !== 200){
                    return;
                }
                state.loggedIn = true;
                state.token = action.payload.data
                localStorage.setItem('codificantesToken', action.payload.data);
            })
            .addCase(loginWithGithub.fulfilled, (state, action:PayloadAction<any>) => {
                if (action.payload.status !== 200){
                    return;
                }
                state.loggedIn = true;
                state.token = action.payload.data
                localStorage.setItem('codificantesToken', action.payload.data);
            })
    }})

export const {logout, loginFromStorage} = LoginSlice.actions;

export const selectLoggedIn = (state: RootState) => state.login.loggedIn;
export const selectToken = (state: RootState) => state.login.token;
export default LoginSlice.reducer;
