import {ISupplier} from "./ISupplier";
import {IProduct} from "./IProduct";
import {IModel} from "./IModel";

export interface IOrderItem {
    product?: IProduct,
    quantity?: number,
}

export interface IOrder extends IModel {
    orderItems: IOrderItem[],
    supplier?: ISupplier
}

export interface IEditOrderItem {
    quantity: number,
    index: number
}
