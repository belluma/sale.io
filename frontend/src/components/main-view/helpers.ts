import {Model, Views} from "../../interfaces/IThumbnailData";

export const views = (Object.keys(Model) as Array<keyof typeof Model>)
export const images = {
    new: '',
    login: '/images/profile.svg',
    employee: '/images/profile.svg',
    product: '',
    customer: '',
    supplier: '',
    order: '',
    error:'/images/error.svg',
    pending: ''
}
export const newItemData = {
    picture:"images/add.svg",
    model:Views.NEW
}


