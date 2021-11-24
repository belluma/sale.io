import {IOrderItem} from "./IOrder";

export interface ICustomer {
    id?: number,
    orderItems: IOrderItem[],
    status: "open" | 'paid'
}

export const emptyCustomer: ICustomer = {
    orderItems: [],
    status: 'open'
}
