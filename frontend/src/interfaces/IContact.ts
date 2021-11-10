import {IModel} from "./IModel";

export interface IContact extends IModel {
    email: string,
    phone: string,
    picture: string,
}
