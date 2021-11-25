import {IProduct} from "../../../interfaces/IProduct";
import {IOrderItem} from "../../../interfaces/IOrder";

export function productsBySupplier (this: string, product:IProduct):boolean {
    if(!product.suppliers || !this) return false;
    return product.suppliers.some(s => s.id === this);
}
export const getSubTotalWholesale = ({product, quantity}: IOrderItem) => {
    return (!product?.purchasePrice || !quantity) ? 0 : product.purchasePrice * quantity;
}
export const getTotalWholeSale = (sum:number, {product, quantity}:IOrderItem):number => {
    const subTotal = getSubTotalWholesale({product, quantity})
    return sum + Math.ceil(subTotal * 100) / 100;
}
export const getSubTotalRetail = ({product, quantity}: IOrderItem) => {
    return (!product?.retailPrice || !quantity) ? 0 : product.retailPrice * quantity;
}
export const getTotalRetail = (sum:number, {product, quantity}:IOrderItem):number => {
    const subTotal = getSubTotalRetail({product, quantity})
    return sum + Math.ceil(subTotal * 100) / 100;
}
