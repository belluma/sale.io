import {ICredentials, IEmployee} from "./IEmployee";
import {IDetailsData} from "./IThumbnailData";

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

export interface IDetailsState {
    showDetails:boolean,
    selectedDetails: IDetailsData | undefined,
}
