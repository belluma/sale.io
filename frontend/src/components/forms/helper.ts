import {ISupplier} from "../../interfaces/ISupplier";
import {Option} from "../../interfaces/INewItem";
import {Weekdays} from "../../interfaces/weekdays";

export const mapSupplierToSelectData = (supplier:ISupplier[]):Option[] =>{
    return supplier.map(s => {
        return {id: s.id, name: `${s.firstName} ${s.lastName}`}
    });
}
export const mapWeekdaysToSelectData = ():Option[] =>{
    return Object.keys(Weekdays).map((d,i) => {
        return {id: i.toString(), name: d}
    });
}
