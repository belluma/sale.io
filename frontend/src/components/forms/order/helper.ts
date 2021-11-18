import {IProduct} from "../../../interfaces/IProduct";
import {IOrderItem} from "../../../interfaces/IOrder";

export function productsBySupplier (this: string, product:IProduct):boolean {
    if(!product.suppliers || !this) return false;
    return product.suppliers.some(s => s.id === this);
}
export const getSubTotal = ({product, quantity}: IOrderItem) => {
    return (!product?.purchasePrice || !quantity) ? 0 : product.purchasePrice * quantity;
}
export const getTotal = (sum:number, {product, quantity}:IOrderItem):number => {
    const subTotal = getSubTotal({product, quantity})
    return sum + Math.ceil(subTotal * 100) / 100;
}
