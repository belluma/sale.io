import { IEmployee } from "./IEmployee";

export interface IEmployeeState {
    employees: IEmployee[],
    currentEmployee: IEmployee | undefined,
}

export interface IAuthState {
    loggedIn: boolean,
    token: string,
}
