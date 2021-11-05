import {IEmployee} from "./IEmployee";
import { IProduct } from "./IProduct";

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

export interface IResponseGetAllProducts extends IResponseBase{
    data: IProduct[];
}
