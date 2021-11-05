import {Views} from "../../interfaces/IThumbnailData";

export const views = (Object.keys(Views) as Array<keyof typeof Views>).map(v => v).filter((v, i) => i > 1);

export const images = {
    none: '',
    login: '/pictures/profile.svg',
    employees: '/pictures/profile.svg',
    products: '',
    customers: '',
    suppliers: '',
}

export const newItemData = {
    none: {title: "",
        picture: "",
        model: Views.NONE},
    login: {title: "",
        picture: "",
        model: Views.LOGIN},
    employees: {
        title: "",
        picture: "",
        model: Views.EMPLOYEES
    },
    products: {
        title: "",
        picture: "",
        model: Views.PRODUCTS
    },
    customers: {
        title: "",
        picture: "",
        model: Views.CUSTOMERS
    },
    suppliers: {
        title: "",
        picture: "",
        model: Views.SUPPLIERS
    },
}
