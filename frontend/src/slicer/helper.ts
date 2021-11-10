import {Dispatch} from "@reduxjs/toolkit";
import {getErrorMessage} from "./errorSlice";

export const handleError = (status:number, statusText: string, dispatch: Dispatch) => {
    if (status !== 200) {
        dispatch(getErrorMessage({status, statusText}))
    }
}
