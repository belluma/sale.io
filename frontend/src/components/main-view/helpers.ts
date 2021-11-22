import {Model, Views} from "../../interfaces/IThumbnail";

export const views = (Object.keys(Model) as Array<keyof typeof Model>)
export const images = {
    new: '',
    login: '/images/profile.svg',
    employee: '/images/profile.svg',
    product: 'images/beer.png',
    customer: 'images/no_image.jpg',
    supplier: 'images/no_image.jpg',
    order: '',
    error:'/images/error.svg',
    success: '',
    none: ''
}
export const newItemData = {
    picture:"images/add.svg",
    model:Views.NEW
}


