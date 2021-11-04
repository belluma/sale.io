import {IEmployee} from "./IEmployee";

export enum Views {
    NONE = "none",
    LOGIN = "login",
    EMPLOYEES = "employees",
    PRODUCTS = "products",
    CUSTOMERS = "customers",
    SUPPLIERS = "suppliers",
}

export interface IThumbnailData {
    title:string,
    subtitle?:string,
    picture: string | undefined,
    id: string | undefined
    alt: string,
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
