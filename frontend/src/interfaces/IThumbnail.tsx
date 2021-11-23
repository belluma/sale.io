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
