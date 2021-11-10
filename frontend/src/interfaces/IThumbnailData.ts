import {IEmployee} from "./IEmployee";
import {IProduct} from "./IProduct";
import {ISupplier} from "./ISupplier";

export enum Buttons {
    EMPLOYEE = "employee",
    PRODUCT = "product",
    CUSTOMER = "customer",
    SUPPLIER = "supplier",
}

export enum Views  {
    NEW = "new",
    LOGIN = "login",
    EMPLOYEE = "employee",
    PRODUCT = "product",
    CUSTOMER = "customer",
    SUPPLIER = "supplier",
}

export interface INewItem {
}

export interface IThumbnailData extends INewItem {
    title: string,
    subtitle?: string,
    id?: string | undefined
    alt?: string,
    picture?: string,
    model: Views,
}

export interface IDetailsData extends IThumbnailData {

}

export const parseEmployeeToThumbnailData = ({firstName, lastName, username, picture}: IEmployee): IDetailsData => {
    return {
        title: `${firstName} ${lastName}`,
        picture: picture,
        id: username,
        alt: "profile picture",
        model: Views.LOGIN

    }
}
export const parseSupplierToThumbnailData = ({firstName, lastName, id, picture}: ISupplier): IDetailsData => {
    return {
        title: `${firstName} ${lastName}`,
        picture: picture,
        id: id,
        alt: "profile picture",
        model: Views.SUPPLIER

    }
}
export const parseProductToThumbnailData = ({
                                                id,
                                                name,
                                                suppliers,
                                                stockCodeSupplier,
                                                category,
                                                picture,
                                                purchasePrice,
                                                retailPrice,
                                                minAmount,
                                                maxAmount,
                                                unitSize
                                            }: IProduct): IDetailsData => {
    return {
        title: name,
        picture: picture,
        id: id?.toString() || "",
        alt: "product picture",
        model: Views.PRODUCT
    }
}
