import {IContact} from "./IContact";

export interface ISupplier extends IContact{
    products: string[],
    orderDay: string,
}
