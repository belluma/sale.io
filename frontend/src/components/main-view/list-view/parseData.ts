import {IProduct} from "../../../interfaces/IProduct";
import {parseName} from "../thumbnail/helper";

export const parseProduct = (product: IProduct) => {


    return {
        ...product, supplier: product.suppliers ? parseName(product.suppliers[0]) : [],
        purchasePrice: `€ ${product.purchasePrice?.toFixed(2)}`,
        retailPrice: `€ ${product.retailPrice?.toFixed(2)}`
    };
}
