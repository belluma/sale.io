import {ISupplier} from "../../interfaces/ISupplier";
import {Option} from "../../interfaces/IForms";
import {Weekday} from "../../interfaces/Weekday";
import {IProduct} from "../../interfaces/IProduct";

export const mapSupplierToSelectData = (supplier:ISupplier[]):Option[] =>{
    return supplier.map(supplier => {
        const id = supplier.id ? supplier.id : '';
        const firstName = supplier.firstName ? supplier.firstName : '';
        const lastName = supplier.lastName ? supplier.lastName : '';
        return {id, name: `${firstName} ${lastName}`};
    });
}
export const mapWeekdaysToSelectData = ():Option[] =>{
    return Object.keys(Weekday).map((day) => {
        return {id: day, name: day};
    });
}

export const mapProductsToSelectData = (products: IProduct[]):Option[] => {
    return products.map(product => {
        const id = product.id ? product.id : '';
        const name = product.name ? product.name : '';
        return {id, name};
    })
}
