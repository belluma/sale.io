import {IContact} from "../../../interfaces/IContact";
import {IEmployee} from "../../../interfaces/IEmployee";
import {ISupplier} from "../../../interfaces/ISupplier";
import {IProduct} from "../../../interfaces/IProduct";
import {IOrder} from "../../../interfaces/IOrder";
import {getTotalWholeSale} from "../../forms/order/helper";
import {IDetailsData, Views} from "../../../interfaces/IThumbnail";
import {ICustomer} from "../../../interfaces/ICustomer";
import {ICategory} from "../../../interfaces/ICategory";

export const parseName = ({firstName="", lastName=""}:IContact):string => {
    let name = '';
    if(firstName) name = `${firstName} `;
    if(lastName) name = name + lastName;
    return name;
}

export const parseEmployeeToThumbnail = ({firstName, lastName, username, picture}: IEmployee): IDetailsData => {
    return {
        title: `${parseName({firstName, lastName})}`,
        picture: `/images/protected/employee/${picture}`,
        id: username,
        alt: "profile picture",
        model: Views.EMPLOYEE
    }
}
export const parseEmployeeToLoginThumbnail = ({firstName, lastName, username, picture}: IEmployee): IDetailsData => {
    return {
        title: `${parseName({firstName, lastName})}`,
        picture: `/images/protected/employee/${picture}`,
        id: username,
        alt: "profile picture",
        model: Views.LOGIN
    }
}
export const parseSupplierToThumbnail = ({firstName, lastName, id, picture}: ISupplier): IDetailsData => {
    return {
        title: `${parseName({firstName, lastName})}`,
        picture: `images/protected/supplier/${picture}`,
        id: id?.toString(),
        alt: "company logo",
        model: Views.SUPPLIER
    }
}
export const parseProductToThumbnail = ({id, name, picture, amountInStock}: IProduct): IDetailsData => {
    return {
        title: name,
        picture: `images/protected/simple/${picture}`,
        id: id?.toString() || "",
        alt: "product picture",
        model: Views.PRODUCT,
        contentText: `in stock: ${amountInStock}`
    }}

export const parseOrderToThumbnail = ({supplier, id, orderItems, status}:IOrder):IDetailsData => {
    return{
        title: `order to ${supplier && parseName(supplier)}`,
        picture: `/images/${status}.png`,
        id: id?.toString() || "",
        model: Views.ORDER,
        contentText: `${orderItems.length} item${orderItems.length > 1 ? "s" : ""}`,
        footerText: `Total: €${orderItems.reduce(getTotalWholeSale, 0).toFixed(2)}`
    }
}
export const parseCustomerToThumbnail = ({ id, orderItems, status, name}:ICustomer):IDetailsData => {

    return{
        title: name || `table ${id}`,
        picture: `/images/${status}.png`,
        id: id?.toString() || "",
        model: Views.CUSTOMER,
        contentText:`total: €${orderItems.reduce(getTotalWholeSale, 0).toFixed(2)}`,
        footerText: `Total: €${orderItems.reduce(getTotalWholeSale, 0).toFixed(2)}`
    }
}




export const parseCategoryToThumbnail = ({name, id}: ICategory, index:number):IDetailsData => {
    return {
        title: name,
        picture: `/images/protected/categories/${name?.split(' ')[0]}.jpg`,
        id: `${id}` || index.toString(),
        model: Views.NONE,
    }
}
