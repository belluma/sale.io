import {IModel} from "./IModel";
import {INames} from "./IEmployee";

export interface IContact extends IModel, INames {
    email: string,
    phone: string,
    picture: string,
}
