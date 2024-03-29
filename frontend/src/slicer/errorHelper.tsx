import {Dispatch} from "@reduxjs/toolkit";
import {setDetailData, showDetails} from "./detailsSlice";
import {IDetailsData, Views} from "../interfaces/IThumbnail";
import {IOrder} from "../interfaces/IOrder";
import {IProduct} from "../interfaces/IProduct";
import {IEmployee} from "../interfaces/IEmployee";
import {ISupplier} from "../interfaces/ISupplier";
import {States} from "../interfaces/IStates";
import {Actions} from "../interfaces/IApiResponse";
import {ICustomer} from "../interfaces/ICustomer";
import {ICategory} from "../interfaces/ICategory";

export interface IError {
    status: number;
    statusText: string;
    data: string
}

export const handleError = (status: number, statusText: string, dispatch: Dispatch) => {
    if (status !== 200) {
        dispatch(setDetailData(parseErrorMessage({status, statusText, data: ""})))
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


type ToSave = IOrder | IProduct | IEmployee | ISupplier | ICustomer | ICategory;

export const setPending = (state: States) => {
    state.pending = true;
}
export const handleApiResponse = (state: States, action: Actions, emptyItem: ToSave) => {
    stopPendingAndHandleError(state, action, emptyItem);
    if (action.payload.status === 200) showSuccessMessage(state);
}

export const stopPendingAndHandleError = (state: States, action: Actions, emptyItem: ToSave) => {
    state.pending = false;
    if (action.payload.status === 200) state.toSave = emptyItem;
    return action.payload.status !== 200;
}
const showSuccessMessage = (state: States) => {
    state.success = true;
}


export const invalidDataError = {data: '', status: 406, statusText: "Not all necessary values are set"}
