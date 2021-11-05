export interface IProduct {
    id?: number,
    name: string,
    suppliers?: any[],
    stockCodeSupplier: string,
    category: Categories,
    picture:string,
    purchasePrice: number,
    retailPrice: number,
    minAmount: number,
    maxAmount: number,
    unitSize: number,
}

export enum Categories  {

}
