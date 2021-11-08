import {ICredentials, IEmployee} from "./IEmployee";
import {IDetailsData, Views} from "./IThumbnailData";
import {IProduct} from "./IProduct";
import {IBody} from "./IApi";
import {ICustomer} from "./ICustomer";
import {ISupplier} from "./ISupplier";

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
    detailsData: IDetailsData ,
}

export const initialDetailsData:IDetailsData = {
    title:"",
    subtitle:"",
    picture: "",
    id: "",
    alt: "",
    model: Views.NEW
}

export interface IProductsState {
    products: IProduct[],
    currentProduct: IProduct | undefined,
    pending:boolean,
}

export interface INewItemState {
    itemToSave: IBody | {},
    pending: boolean,
    savedItem?: IBody
}

export interface IAPIState {
    customers: ICustomer[],
    employees: IEmployee[],
    products: IProduct[],
    suppliers: ISupplier[],
    pending: boolean,
    selectedEntity: ICustomer | IEmployee | IProduct | ISupplier | undefined,
}
