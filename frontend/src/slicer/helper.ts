import {States} from "../interfaces/IStates";
import {Actions} from "../interfaces/IApiResponse";



export const setPending = (state: States) => {
    state.pending = true;
}
export const stopPendingAndHandleError = (state:States, action:Actions) => {
    state.pending = false;
    return action.payload.status !== 200;
}
