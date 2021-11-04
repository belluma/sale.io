import axios from "axios";
import {parseError} from './errorService';


export const getAllProducts = (token: string) => {
    return axios({
        method: 'get',
        url: `/api/product`,
        headers: {"Content-Type": "application/json", "Authorization": `Bearer ${token}`}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const getSingleProduct = (token:string, id:number) => {
    return axios({
        method: 'get',
        url: `/api/product/id`,
        headers: {"Content-Type": "application/json", "Authorization": `Bearer ${token}`}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const createProduct = (token:string, product:IProduct) => {
    return axios({
        method: 'post',
        url: `/api/product`,
        headers: {"Content-Type": "application/json", "Authorization": `Bearer ${token}`}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const editProduct = (token:string, product: IProduct) => {
    return axios({
        method: 'put',
        url: `/api/product/id`,
        headers: {"Content-Type": "application/json", "Authorization": `Bearer ${token}`}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}


