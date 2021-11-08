export enum Categories {
    TEST = "test"
}

export class Product {
    id? = 1;
    name = "";
    suppliers? = [];
    stockCodeSupplier = "";
    category = Categories.TEST;
    picture = "";
    purchasePrice = 1;
    retailPrice = 1;
    minAmount = 1;
    maxAmount = 1;
    unitSize = 1;
}

export interface IProduct extends Product {

}
