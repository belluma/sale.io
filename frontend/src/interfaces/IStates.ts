import {IUserCredentials, IEmployee} from "./IEmployee";
import {IDetailsData, Views} from "./IThumbnailData";
import {IProduct, IProductBuilder} from "./IProduct";
import {IBody} from "./IApi";
import {ICustomer} from "./ICustomer";
import {ISupplier} from "./ISupplier";

export interface IEmployeeState {
    employees: IEmployee[],
    currentEmployee: IEmployee | undefined,
    currentEmployeeCredentials: IUserCredentials | undefined,
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

interface IBaseState {
    pending:boolean
}

export interface IProductsState extends IBaseState {
    products: IProduct[],
    currentProduct: IProduct | undefined,
    productToSave: IProductBuilder,
}

export interface ISuppliersState extends IBaseState {
    suppliers: ISupplier[],
    currentSupplier: ISupplier | undefined,
}

export interface INewItemState extends IBaseState{
    itemToSave: IBody | {},
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
