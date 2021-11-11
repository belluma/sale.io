import {Views} from "../../interfaces/IThumbnailData";

export const views = (Object.keys(Views) as Array<keyof typeof Views>).slice(2)
export const images = {
    new: '',
    login: '/images/profile.svg',
    employee: '/images/profile.svg',
    product: '',
    customer: '',
    supplier: '',
    order: ''
}
export const newItemData = {
    picture:"images/add.svg",
    model:Views.NEW
}


