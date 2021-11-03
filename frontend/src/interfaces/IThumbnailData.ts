import {IEmployee} from "./IEmployee";

export interface IThumbnailData {
    title:string,
    subtitle?:string,
    picture: string | undefined,
}

export const parseEmployeeToThumbnailData = (employee:IEmployee):IThumbnailData => {
    return {
        title: `${employee.firstName} ${employee.lastName}`,
        picture: employee.picture,
    }
}
