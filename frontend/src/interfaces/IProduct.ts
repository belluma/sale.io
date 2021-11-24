import {ISupplier} from "./ISupplier";
import {ICategory} from "./ICategory";


export interface IProduct {
    id?: string,
    name?: string
    suppliers?: ISupplier[];
    stockCodeSupplier?: string
    category?: ICategory,
    picture?: string
    purchasePrice?: number,
    retailPrice?: number,
    minAmount?: number,
    maxAmount?: number,
    unitSize?: number,
    amountInStock?:number,
}

export const emptyProduct: IProduct = {
    name: '',
    suppliers: [],
    purchasePrice: 0,
    retailPrice: 0,
    minAmount: 0,
    maxAmount: 0,
    unitSize: 0,
}
