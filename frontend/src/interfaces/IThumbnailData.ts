import {IEmployee} from "./IEmployee";

export enum Model {
    NONE = "none",
    LOGIN = "login",
}

export interface IThumbnailData {
    title:string,
    subtitle?:string,
    picture: string | undefined,
    id: string | undefined
    alt: string,
    model: Model,
}
export interface IDetailsData extends IThumbnailData{

}

export const parseEmployeeToThumbnailData = (employee:IEmployee):IDetailsData => {
    return {
        title: `${employee.firstName} ${employee.lastName}`,
        picture: employee.picture,
        id:employee.username,
        alt: "profile picture",
        model: Model.LOGIN

    }
}
