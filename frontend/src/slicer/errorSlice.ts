import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../app/store";

export interface IError  {
    status:number,
    statusText:string,
}

const initialState = {
    error: false,
    status: 200,
    statusText: "",
}

export const ErrorSlice = createSlice({
    name:'error',
    initialState,
    reducers:{
        closeError: (state) => {
            state.error = false;
            state.status = 200;
            state.statusText = "";
        },
        getErrorMessage:(state, action:PayloadAction<IError>) => {
            state.error = true;
            state.status = action.payload.status;
            state.statusText = action.payload.statusText;
        }
    }
})

export const selectError = (state: RootState) => state.error.error;
export const selectStatus = (state: RootState) => state.error.status;
export const selectStatusText = (state: RootState) => state.error.statusText;
export const {closeError, getErrorMessage} = ErrorSlice.actions;
export default ErrorSlice.reducer;
