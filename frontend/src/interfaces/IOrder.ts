import {ISupplier} from "./ISupplier";
import {IProduct} from "./IProduct";
import {IModel} from "./IModel";

interface orderItem {
    product: IProduct,
    quantity: number,
}

export interface IOrder extends IModel {
    items: orderItem[],
    supplier: ISupplier
}
