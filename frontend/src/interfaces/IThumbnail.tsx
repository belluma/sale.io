import {IEmployee} from "./IEmployee";
import {IProduct} from "./IProduct";
import {ISupplier} from "./ISupplier";
import {IOrder} from "./IOrder";
import {IContact} from "./IContact";
import {getTotal} from "../components/forms/order/helper";

export enum Model {
    EMPLOYEE = "employee",
    PRODUCT = "product",
    CUSTOMER = "customer",
    SUPPLIER = "supplier",
    ORDER = "order",
}


export enum Views {
    NEW = "new",
    LOGIN = "login",
    ERROR = "error",
    EMPLOYEE = "employee",
    PRODUCT = "product",
    CUSTOMER = "customer",
    SUPPLIER = "supplier",
    ORDER = "order",
    NONE = "none",
}

export interface INewItem {
}

export interface IThumbnail extends INewItem {
    title?: string,
    subtitle?: string,
    id?: string |   undefined,
    alt?: string,
    picture?: string,
    model: Views,
    contentText?:string,
    footerText?:string,
}

export interface IDetailsData extends IThumbnail {
}
export const parseName = ({firstName="", lastName=""}:IContact):string => {
    let name = '';
    if(firstName) name = `${firstName} `;
    if(lastName) name = name + lastName;
    return name;
}

export const parseEmployeeToThumbnail = ({firstName, lastName, username, picture}: IEmployee): IDetailsData => {
    return {
        title: `${parseName({firstName, lastName})}`,
        picture: `/images/protected/employee/${picture}`,
        id: username,
        alt: "profile picture",
        model: Views.EMPLOYEE
    }
}
export const parseEmployeeToLoginThumbnail = ({firstName, lastName, username, picture}: IEmployee): IDetailsData => {
    return {
        title: `${parseName({firstName, lastName})}`,
        picture: `/images/protected/employee/${picture}`,
        id: username,
        alt: "profile picture",
        model: Views.LOGIN
    }
}
export const parseSupplierToThumbnail = ({firstName, lastName, id, picture}: ISupplier): IDetailsData => {
    return {
        title: `${parseName({firstName, lastName})}`,
        picture: `images/protected/supplier/${picture}`,
        id: id?.toString(),
        alt: "company logo",
        model: Views.SUPPLIER
    }
}
export const parseProductToThumbnail = ({id, name, picture, amountInStock}: IProduct): IDetailsData => {
    return {
        title: name,
        picture: `images/protected/simple/${picture}`,
        id: id?.toString() || "",
        alt: "product picture",
        model: Views.PRODUCT,
        contentText: `in stock: ${amountInStock}`
    }}

    export const parseOrderToThumbnail = ({supplier, id, orderItems, status}:IOrder):IDetailsData => {
        return{
            title: `order to ${supplier && parseName(supplier)}`,
            picture: `/images/${status}.png`,
            id: id?.toString() || "",
            model: Views.ORDER,
            contentText: `${orderItems.length} item${orderItems.length > 1 ? "s" : ""}`,
            footerText: `Total: €${orderItems.reduce(getTotal, 0).toFixed(2)}`
        }
}
