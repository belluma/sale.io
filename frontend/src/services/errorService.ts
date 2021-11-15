import {AxiosError} from "axios";
import {IError} from "../slicer/errorHelper";

interface IApiError {
    message: string,
    timeStamp: Date,
    exceptionTrail?: string,
}

export const parseError = ({response}: AxiosError<IApiError>):IError => {
    const message = response?.data.message ? response.data.message : response?.statusText;
    const status = response?.status ? response?.status : 400
    return {data: "", status:status , statusText: message ? message : ""}
}
