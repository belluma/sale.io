import {Dispatch} from "@reduxjs/toolkit";
import {setDetailData, showDetails} from "./detailsSlice";
import {IDetailsData, Views} from "../interfaces/IThumbnailData";

export interface IError {
    status?: number;
    statusText?: string;
    data?:string
}
export const handleError = (status:number, statusText: string, dispatch: Dispatch) => {
    if (status !== 200) {
        dispatch(setDetailData(parseErrorMessage({status, statusText})))
        dispatch(showDetails())
    }
}


const parseErrorMessage = ({ statusText}:IError):IDetailsData => {
    return {
        title:"Something went wrong!",
        subtitle:statusText,
        model:Views.ERROR,
    }
}

export const invalidDataError = {data: '', status: 406, statusText: "Not all necessary values are set"}
