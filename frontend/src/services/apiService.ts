import axios from "axios";
import {parseError} from './errorService';
import {authHeaders, jsonHeaders} from "./serviceUtils";
import {IBody} from "../interfaces/IApi";


export const getAll = (model: string, token: string) => {
    return axios({
        method: 'get',
        url: `/api/${model}s`,
        headers: {...jsonHeaders(), ...authHeaders(token)}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const getOne = (model: string, token: string, id: string) => {
    return axios({
        method: 'get',
        url: `/api/${model}s/${id}`,
        headers: {...jsonHeaders(), ...authHeaders(token)}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const create = (model: string, token: string, data: IBody) => {
    return axios({
        method: 'post',
        url: `/api/${model}s`,
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
        url: `/api/${model}s/${data.id}`,
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
        url: `/api/${model}s/${id}`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}


