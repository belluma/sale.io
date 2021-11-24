import {IEmployee} from "./IEmployee";
import {IProduct} from "./IProduct";
import {ICustomer} from "./ICustomer";
import {ISupplier} from "./ISupplier";
import {IOrder} from "./IOrder";
import {PayloadAction} from "@reduxjs/toolkit";

interface IResponseBase {
    status: number,
    statusText: string
}

export interface IResponseData extends IResponseBase {
    data: string;

}

export interface IResponseGetAllEmployees extends IResponseBase {
    data: IEmployee[];
}

export interface IResponseGetOneEmployee extends IResponseBase {
    data: IEmployee;
}

export interface IResponseGetAllProducts extends IResponseBase {
    data: IProduct[];
}

export interface IResponseGetOneProduct extends IResponseBase {
    data: IProduct;
}

export interface IResponseGetAllSuppliers extends IResponseBase {
    data: ISupplier[];
}

export interface IResponseGetOneSupplier extends IResponseBase {
    data: ISupplier;
}

export interface IResponseGetAllOrders extends IResponseBase {
    data: IOrder[];
}

export interface IResponseGetOneOrder extends IResponseBase {
    data: IOrder;
}
export interface IResponseGetAllCustomers extends IResponseBase {
    data: ICustomer[];
}

export interface IResponseGetOneCustomer extends IResponseBase {
    data: ICustomer;
}

export type Actions =
    PayloadAction<IResponseGetAllEmployees>
    | PayloadAction<IResponseGetOneEmployee>
    | PayloadAction<IResponseGetAllProducts>
    | PayloadAction<IResponseGetOneProduct>
    | PayloadAction<IResponseGetAllSuppliers>
    | PayloadAction<IResponseGetOneSupplier>
    | PayloadAction<IResponseGetAllOrders>
    | PayloadAction<IResponseGetOneOrder>
    | PayloadAction<IResponseGetAllCustomers>
    | PayloadAction<IResponseGetOneCustomer>


export interface IApiResponse {
    status: number,
    statusText: string,
    data: string | IEmployee[] | ICustomer[] | IProduct[] | ISupplier[],
}

export interface IApiPayload extends IApiResponse {
    model: "employees"
}
