import {IUserCredentials, IEmployee} from "./IEmployee";
import {IDetailsData, Views} from "./IThumbnailData";
import {IProduct} from "./IProduct";
import {ICustomer} from "./ICustomer";
import {ISupplier} from "./ISupplier";
import {IOrder} from "./IOrder";

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
    model: Views.NEW,
    error: false
}

interface IBaseState {
    pending:boolean
}

export interface IProductsState extends IBaseState {
    products: IProduct[],
    currentProduct: IProduct | undefined,
    productToSave: IProduct,
}

export interface ISuppliersState extends IBaseState {
    suppliers: ISupplier[],
    currentSupplier: ISupplier | undefined,
    supplierToSave: ISupplier,
}

export interface IOrdersState extends IBaseState {
    orders: IOrder[],
    currentOrder: IOrder | undefined,
    orderToSave: IOrder,
}


export interface IAPIState {
    customers: ICustomer[],
    employees: IEmployee[],
    products: IProduct[],
    suppliers: ISupplier[],
    pending: boolean,
    selectedEntity: ICustomer | IEmployee | IProduct | ISupplier | undefined,
}
