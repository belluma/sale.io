import {Views} from "../../interfaces/IThumbnailData";

export const views = (Object.keys(Views) as Array<keyof typeof Views>).map(v => v).filter((v, i) => i > 1);

export const images = {
    new: '',
    login: '/images/profile.svg',
    employees: '/images/profile.svg',
    products: '',
    customers: '',
    suppliers: '',
}
export const newItemData = {
    picture:"images/add.svg",
    model:Views.NEW
}
