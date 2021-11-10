import {ISupplier} from "../../interfaces/ISupplier";
import {Option} from "../../interfaces/INewItem";
import {Weekdays} from "../../interfaces/weekdays";

export const mapSupplierToSelectData = (supplier:ISupplier[]):Option[] =>{
    return supplier.map(s => {
        const id = s.id ? s.id : '';
        const firstName = s.firstName ? s.firstName : '';
        const lastName = s.lastName ? s.lastName : '';
        return {id, name: `${firstName} ${lastName}`}
    });
}
export const mapWeekdaysToSelectData = ():Option[] =>{
    return Object.keys(Weekdays).map((d,i) => {
        return {id: i.toString(), name: d}
    });
}
