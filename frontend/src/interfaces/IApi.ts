import {IEmployee} from "./IEmployee";
import {IProduct} from "./IProduct";
import {ICustomer} from "./ICustomer";
import {ISupplier} from "./ISupplier";
import {ICategory} from "./ICategory";
import {IOrderItem} from "./IOrder";

export interface IOrderToCustomer {
    id?: number,
    order: ICustomer,
    itemToAddOrRemove: IOrderItem
}

export type IBody =IEmployee | IProduct | ICustomer | ISupplier | ICategory | IOrderToCustomer
