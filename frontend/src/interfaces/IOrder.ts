import {ISupplier} from "./ISupplier";
import {IProduct} from "./IProduct";
import {IModel} from "./IModel";
import {OrderStatus} from "./OrderStatus";

export interface IOrderItem {
    product?: IProduct,
    quantity?: number,
}

export interface IOrder extends IModel {
    orderItems: IOrderItem[],
    supplier?: ISupplier,
    orderStatus?: OrderStatus
}

export interface IEditOrderItem {
    quantity: number,
    index: number
}

export const emptyOrder:IOrder = {
    orderItems: []
}
