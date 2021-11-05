import {Views} from "../../interfaces/IThumbnailData";

export const images = {
    none: '',
    login: '/pictures/profile.svg',
    employees: '/pictures/profile.svg',
    products:'',
    customers:'',
    suppliers:'',
}

export const views = (Object.keys(Views) as Array<keyof typeof Views>).map(v => v).filter((v,i) => i > 1);
