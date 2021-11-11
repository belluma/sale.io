import {ISupplier} from "../../interfaces/ISupplier";
import {Option} from "../../interfaces/IForms";
import {Weekdays} from "../../interfaces/weekdays";
import {IProduct} from "../../interfaces/IProduct";

export const mapSupplierToSelectData = (supplier:ISupplier[]):Option[] =>{
    return supplier.map(s => {
        const id = s.id ? s.id : '';
        const firstName = s.firstName ? s.firstName : '';
        const lastName = s.lastName ? s.lastName : '';
        return {id, name: `${firstName} ${lastName}`};
    });
}
export const mapWeekdaysToSelectData = ():Option[] =>{
    return Object.keys(Weekdays).map((d,i) => {
        return {id: i.toString(), name: d};
    });
}

export const mapProductsToSelectData = (products: IProduct[]):Option[] => {
    return products.map(p => {
        const id = p.id ? p.id : '';
        const name = p.name ? p.name : '';
        return {id, name};
    })
}
