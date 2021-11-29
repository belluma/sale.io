import axios from "axios";
import {parseError} from './errorService';
import {authHeaders, jsonHeaders} from "./serviceUtils";
import {IBody} from "../interfaces/IApi";
import {IOrder, IOrderItem} from "../interfaces/IOrder";


export const getAll = (model: string, token: string) => {
    return axios({
        method: 'get',
        url: `/api/${model}`,
        headers: {...jsonHeaders(), ...authHeaders(token)}
    }).then(response => {
        return response
    })
        .catch(err => {
            return parseError(err)
        })
}

export const getOne = (model: string, token: string, id: string) => {
    return axios({
        method: 'get',
        url: `/api/${model}/${id}`,
        headers: {...jsonHeaders(), ...authHeaders(token)}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const create = (model: string, token: string, data: IBody) => {
    return axios({
        method: 'post',
        url: `/api/${model}`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
        data,
    }).then(response => {

        return response
    })
        .catch(err => parseError(err))
}

export const edit = (model: string, token: string, data: IBody) => {
    return axios({
        method: 'put',
        url: `/api/${model}/${data.id}`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
        data
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const del = (model: string, token: string, id: string) => {
    return axios({
        method: 'delete',
        url: `/api/${model}/${id}`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const receiveOrder = (token: string, data: IOrder) => {
    return axios({
        method: 'put',
        url: `/api/orders_suppliers/?id=${data.id}&status=RECEIVED`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
        data
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const addItemsToOrder = (token: string, orderId: number, data: IOrderItem) => {
    return axios({
        method: 'put',
        url: `/api/orders_customers/add/?id=${orderId}`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
        data
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const takeItemsOffOrder = (token: string, orderId: number, data: IOrderItem) => {
    return axios({
        method: 'put',
        url: `/api/orders_customers/remove/?id=${orderId}`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
        data
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}


