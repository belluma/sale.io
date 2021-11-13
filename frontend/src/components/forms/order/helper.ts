import {IProduct} from "../../../interfaces/IProduct";

export function productsBySupplier (this: string, product:IProduct):boolean {
    if(!product.suppliers || !this) return false;
    return product.suppliers.some(s => s.id === this);
}
