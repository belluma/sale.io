import {States} from "../interfaces/IStates";
import {Actions} from "../interfaces/IApiResponse";
import {IOrder} from "../interfaces/IOrder";
import {IProduct} from "../interfaces/IProduct";
import {ISupplier} from "../interfaces/ISupplier";
import {IEmployee} from "../interfaces/IEmployee";


type ToSave = IOrder | IProduct | IEmployee | ISupplier;

export const setPending = (state: States) => {
    state.pending = true;
}
export const stopPendingAndHandleError = (state:States, action:Actions, emptyItem: ToSave) => {
    state.pending = false;
    console.log(action.payload.status);
    if(action.payload.status === 200) state.toSave = emptyItem;
    return action.payload.status !== 200;
}
