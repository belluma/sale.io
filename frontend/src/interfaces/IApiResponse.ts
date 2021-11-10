import {IEmployee} from "./IEmployee";
import { IProduct } from "./IProduct";
import {ICustomer} from "./ICustomer";
import {ISupplier} from "./ISupplier";

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
export interface IResponseGetAllSuppliers extends IResponseBase{
    data: ISupplier[];
}

export interface IApiResponse {
    status: number,
    statusText:string,
    data: string | IEmployee[]  | ICustomer[] | IProduct[] | ISupplier[],
}
export interface IApiPayload extends IApiResponse {
    model: "employees"
}
