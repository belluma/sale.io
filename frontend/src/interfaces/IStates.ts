import { IEmployee } from "./IEmployee";

export interface IEmployeeState {
    employees: IEmployee[] | undefined,
    currentEmployee: IEmployee | undefined,
    pending: boolean
}

export interface IAuthState {
    loggedIn: boolean,
    token: string,
}
