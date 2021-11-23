import {IOrderItem} from "./IOrder";

export interface ICustomer {
    id?: number,
    orderItems: IOrderItem[]
}

export const emptyCustomer: ICustomer = {
    orderItems: []
}
