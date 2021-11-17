import {IUserCredentials, IEmployee} from "./IEmployee";
import {IDetailsData, Views} from "./IThumbnailData";
import {IProduct} from "./IProduct";
import {ICustomer} from "./ICustomer";
import {ISupplier} from "./ISupplier";
import {IOrder} from "./IOrder";

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
    model: Views.NEW,
}

interface IBaseState {
    pending:boolean,
    success: boolean,
}

export interface IEmployeeState extends IBaseState{
    employees: IEmployee[],
    current: IEmployee | undefined,
    currentEmployeeCredentials: IUserCredentials | undefined,
    toSave: IEmployee
}

export interface IProductsState extends IBaseState {
    products: IProduct[],
    current: IProduct | undefined,
    toSave: IProduct,
}

export interface ISuppliersState extends IBaseState {
    suppliers: ISupplier[],
    current: ISupplier | undefined,
    toSave: ISupplier,
}

export interface IOrdersState extends IBaseState {
    orders: IOrder[],
    current: IOrder | undefined,
    toSave: IOrder,
}

export type States = IProductsState | ISuppliersState | IOrdersState | IEmployeeState


export interface IAPIState {
    customers: ICustomer[],
    employees: IEmployee[],
    products: IProduct[],
    suppliers: ISupplier[],
    pending: boolean,
    selectedEntity: ICustomer | IEmployee | IProduct | ISupplier | undefined,
}
