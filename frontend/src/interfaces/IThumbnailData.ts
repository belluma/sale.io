import {IEmployee} from "./IEmployee";

export enum Views {
    NONE = "none",
    LOGIN = "login",
    EMPLOYEES = "employees",
    PRODUCTS = "products",
    CUSTOMERS = "customers",
    SUPPLIERS = "suppliers",
}
export interface INewItem{

}

export interface IThumbnailData extends INewItem{
    title: string,
    subtitle?:string,
    id?: string | undefined
    alt?: string,
    picture?: string,
    model: Views,
}
export interface IDetailsData extends IThumbnailData{

}

export const parseEmployeeToThumbnailData = (employee:IEmployee):IDetailsData => {
    return {
        title: `${employee.firstName} ${employee.lastName}`,
        picture: employee.picture,
        id:employee.username,
        alt: "profile picture",
        model: Views.LOGIN

    }
}
