import {Dispatch} from "@reduxjs/toolkit";
import {setDetailData, showDetails} from "./detailsSlice";
import {IDetailsData, Views} from "../interfaces/IThumbnailData";
import {IOrder} from "../interfaces/IOrder";
import {IProduct} from "../interfaces/IProduct";
import {IEmployee} from "../interfaces/IEmployee";
import {ISupplier} from "../interfaces/ISupplier";
import {States} from "../interfaces/IStates";
import {Actions} from "../interfaces/IApiResponse";

export interface IError {
    status: number;
    statusText: string;
    data: string
}

export const handleError = (status: number, statusText: string, dispatch: Dispatch) => {
    if (status !== 200) {
        dispatch(setDetailData(parseErrorMessage({status, statusText, data:""})))
        dispatch(showDetails())
    }
}

const parseErrorMessage = ({statusText}: IError): IDetailsData => {
    return {
        title: "Something went wrong!",
        subtitle: statusText,
        model: Views.ERROR,
    }
}
type ToSave = IOrder | IProduct | IEmployee | ISupplier;

export const setPending = (state: States) => {
    state.pending = true;
}
export const stopPendingAndHandleError = (state:States, action:Actions, emptyItem: ToSave) => {
    state.pending = false;
    state.success = true;
    if(action.payload.status === 200) state.toSave = emptyItem;
    return action.payload.status !== 200;
}


export const invalidDataError = {data: '', status: 406, statusText: "Not all necessary values are set"}
