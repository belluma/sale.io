import {IContact} from "./IContact";

export interface INames {
    firstName: string,
    lastName: string,
}

interface ICredentials {
    username: string,
    password: string,
}

export interface IUserCredentials extends INames, ICredentials {

}

export interface IEmployee extends IContact, IUserCredentials {
    email: string,
    phone: string,
    picture: string,
    id: string,
}

export const initialCredentials: IUserCredentials = {
    firstName: "",
    lastName: "",
    password: "",
    username: ""
}
