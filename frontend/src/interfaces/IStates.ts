import {ICredentials, IEmployee} from "./IEmployee";

export interface IEmployeeState {
    employees: IEmployee[],
    currentEmployee: IEmployee | undefined,
    currentEmployeeCredentials: ICredentials | undefined,
    pending: boolean
}

export interface IAuthState {
    loggedIn: boolean,
    token: string,
}
