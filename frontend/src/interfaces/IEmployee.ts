import {IContact} from "./IContact";
import {IModel} from "./IModel";

export interface INames {
    firstName?: string,
    lastName?: string,
}

interface ICredentials {
    username: string,
    password: string,
}

export interface IUserCredentials extends INames, ICredentials {

}

export interface IEmployee extends IContact, IUserCredentials, IModel {

}

export const initialCredentials: IUserCredentials = {
    firstName: "",
    lastName: "",
    password: "",
    username: ""
}
