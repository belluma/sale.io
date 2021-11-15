import {IEmployee} from "./IEmployee";
import { IProduct } from "./IProduct";
import {ICustomer} from "./ICustomer";
import {ISupplier} from "./ISupplier";
import {IOrder} from "./IOrder";

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
export interface IResponseGetOneEmployee extends IResponseBase{
    data: IEmployee;
}
export interface IResponseGetAllProducts extends IResponseBase{
    data: IProduct[];
}
export interface IResponseGetOneProduct extends IResponseBase{
    data: IProduct;
}
export interface IResponseGetAllSuppliers extends IResponseBase{
    data: ISupplier[];
}
export interface IResponseGetOneSupplier extends IResponseBase{
    data: ISupplier;
}
export interface IResponseGetAllOrders extends IResponseBase{
    data: IOrder[];
}
export interface IResponseGetOneOrder extends IResponseBase{
    data: IOrder;
}

export interface IApiResponse {
    status: number,
    statusText:string,
    data: string | IEmployee[]  | ICustomer[] | IProduct[] | ISupplier[],
}
export interface IApiPayload extends IApiResponse {
    model: "employees"
}
