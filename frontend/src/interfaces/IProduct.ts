import {ISupplier} from "./ISupplier";

export enum Categories {
    TEST = "test"
}

// export interface IProduct {
//     id?:string,
//     name:string
//     suppliers?: ISupplier[];
//     stockCodeSupplier:string
//     category:Categories,
//     picture:string
//     purchasePrice:number,
//     retailPrice:number,
//     minAmount:number,
//     maxAmount:number,
//     unitSize:number,
// }
export interface IProduct {
    id?:string,
    name?:string
    suppliers?: ISupplier[];
    stockCodeSupplier?:string
    category?:Categories,
    picture?:string
    purchasePrice?:number,
    retailPrice?:number,
    minAmount?:number,
    maxAmount?:number,
    unitSize?:number,
}
