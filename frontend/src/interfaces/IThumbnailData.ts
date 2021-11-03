import {IEmployee} from "./IEmployee";

export interface IThumbnailData {
    title:string,
    subtitle?:string,
    picture: string | undefined,
    id: string | undefined
    alt: string
}

export interface IDetailsData extends IThumbnailData{

}
export const parseEmployeeToThumbnailData = (employee:IEmployee):IDetailsData => {
    return {
        title: `${employee.firstName} ${employee.lastName}`,
        picture: employee.picture,
        id:employee.username,
        alt: "profile picture"
    }
}
