import {IEmployee} from "./IEmployee";

export interface IThumbnailData {
    title:string,
    subtitle?:string,
    picture: string | undefined,
    id: string | undefined
    alt: string
}

export const parseEmployeeToThumbnailData = (employee:IEmployee):IThumbnailData => {
    return {
        title: `${employee.firstName} ${employee.lastName}`,
        picture: employee.picture,
        id:employee.id,
        alt: "profile picture"
    }
}
