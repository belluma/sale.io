import {IProduct} from "../../../interfaces/IProduct";
import {parseName} from "../thumbnail/helper";
import {IEmployee} from "../../../interfaces/IEmployee";

export const parseProduct = (product: IProduct) => {
    return {
        ...product, supplier: product.suppliers ? parseName(product.suppliers[0]) : [],
        purchasePrice: `€ ${product.purchasePrice?.toFixed(2)}`,
        retailPrice: `€ ${product.retailPrice?.toFixed(2)}`
    };
}
export const parseEmployee = (employee: IEmployee) => {
    const {username, password, ...data} = employee;
    return {...data, id: username};
}
