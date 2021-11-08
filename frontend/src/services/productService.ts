import axios from "axios";
import {parseError} from './errorService';
import {IProduct} from "../interfaces/IProduct";
import {authHeaders, jsonHeaders} from "./serviceUtils";


export const getAllProducts = (token: string) => {
    return axios({
        method: 'get',
        url: `/api/product`,
        headers: {...jsonHeaders(), ...authHeaders(token)}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const getSingleProduct = (token:string, id:number) => {
    return axios({
        method: 'get',
        url: `/api/product/${id}`,
        headers: {...jsonHeaders(), ...authHeaders(token)}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const createProduct = (token:string, product:IProduct) => {
    return axios({
        method: 'post',
        url: `/api/product`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
        data: (product),
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const editProduct = (token:string, product: IProduct) => {
    return axios({
        method: 'put',
        url: `/api/product/id`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
        data:{product}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}


