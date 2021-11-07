import {IProduct, Product} from "../../interfaces/IProduct";

const productLabels = Object.keys(new Product()).slice(1) as Array<keyof IProduct>;
const productFormType = Object.values(new Product()).slice(1) as Array<keyof IProduct>;

interface IFormData {
    labels:string[],
    formTypes: any[]
}

const productData: IFormData= {
    labels: productLabels,
    formTypes: productFormType,
}

export const formLabelsAndTypes = {
    none: {labels: [], formType: []},
    employee: productData,
    product: productData,
    customer: productData,
    supplier: productData,
}
