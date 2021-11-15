import {IContact} from "./IContact";
import {Weekdays} from "./weekdays";

export interface ISupplier extends IContact{
    products?: string[],
    orderDay?: string,
}

export const emptySupplier:ISupplier = {
    email: '',
    phone: '',
    picture: '',
    products: [],
    // orderDay?: string,
}
