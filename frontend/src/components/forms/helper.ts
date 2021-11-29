import {ISupplier} from "../../interfaces/ISupplier";
import {Option} from "../../interfaces/IForms";
import {Weekday} from "../../interfaces/Weekday";
import {IProduct} from "../../interfaces/IProduct";
import {ICategory} from "../../interfaces/ICategory";

export const mapSupplierToSelectData = (supplier: ISupplier[]): Option[] => {
    return supplier.map(supplier => {
        const id = supplier.id ? supplier.id : '';
        const firstName = supplier.firstName ? supplier.firstName : '';
        const lastName = supplier.lastName ? supplier.lastName : '';
        return {id, name: `${firstName} ${lastName}`};
    });
}

export const mapCategoryToSelectData = (categories: ICategory[]): Option[] => {
    return categories.map(category => {
        const id = category.id ? category.id.toString() : '';
        const name = category.name ? category.name : '';
        return {id, name}
    })
}
export const mapWeekdaysToSelectData = (): Option[] => {
    return Object.keys(Weekday).map((day) => {
        return {id: day, name: day};
    });
}

export const mapProductsToSelectData = (products: IProduct[]): Option[] => {
    return products.map(product => {
        const id = product.id ? product.id : '';
        const name = product.name ? product.name : '';
        return {id, name};
    })
}

