import {IUserCredentials, IEmployee} from "./IEmployee";
import {IDetailsData, Views} from "./IThumbnail";
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
    goBack: boolean,
}

export const initialDetailsData:IDetailsData = {
    title:"",
    subtitle:"",
    picture: "",
    id: "",
    alt: "",
    model: Views.NONE,
}

interface IBaseState {
    pending:boolean,
    success: boolean,
}

export interface IEmployeeState extends IBaseState{
    employees: IEmployee[],
    current: IEmployee ,
    currentEmployeeCredentials: IUserCredentials | undefined,
    toSave: IEmployee
}

export interface IProductsState extends IBaseState {
    products: IProduct[],
    current: IProduct ,
    toSave: IProduct,
}

export interface ISuppliersState extends IBaseState {
    suppliers: ISupplier[],
    current: ISupplier,
    toSave: ISupplier,
}

export interface IOrdersState extends IBaseState {
    orders: IOrder[],
    current: IOrder ,
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

export interface ICustomersState extends IBaseState{
    customers: ICustomer[],
    current: ICustomer,
    toSave: ICustomer
}
