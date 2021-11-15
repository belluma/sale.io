import {ISupplier} from "./ISupplier";

export enum Categories {
    TEST = "test"
}

export interface IProduct {
    id?: string,
    name?: string
    suppliers?: ISupplier[];
    stockCodeSupplier?: string
    category?: Categories,
    picture?: string
    purchasePrice?: number,
    retailPrice?: number,
    minAmount?: number,
    maxAmount?: number,
    unitSize?: number,
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
