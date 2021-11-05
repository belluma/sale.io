import {Views} from "../../interfaces/IThumbnailData";

export const views = (Object.keys(Views) as Array<keyof typeof Views>).map(v => v).filter((v, i) => i > 1);

export const images = {
    none: '',
    login: '/images/profile.svg',
    employees: '/images/profile.svg',
    products: '',
    customers: '',
    suppliers: '',
}
export const newItemData = {
    none: {
        title: "",
        picture: "images/add.svg",
        model: Views.NONE
    },
    login: {
        title: "",
        picture: "images/add.svg",
        model: Views.LOGIN
    },
    employees: {
        title: "",
        picture: "images/add.svg",
        model: Views.EMPLOYEES
    },
    products: {
        title: "new product",
        picture: "images/add.svg",
        model: Views.PRODUCTS
    },
    customers: {
        title: "",
        picture: "images/add.svg",
        model: Views.CUSTOMERS
    },
    suppliers: {
        title: "",
        picture: "images/add.svg",
        model: Views.SUPPLIERS
    },
}
