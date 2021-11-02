import {IEmployee} from "./IEmployee";

interface IResponseBase {
    status: number,
    statusText: string
}

export interface IResponseData extends IResponseBase{
    data: string;

}
export interface IResponseGetAllEmployees extends IResponseBase{
    data: IEmployee[];
}
