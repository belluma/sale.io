import axios from "axios";
import {parseError} from './errorService';
import {authHeaders, jsonHeaders} from "./serviceUtils";
import {IBody} from "../interfaces/IApi";


export const getAll = (type: string, token: string) => {
    return axios({
        method: 'get',
        url: `/api/{${type}s`,
        headers: {...jsonHeaders(), ...authHeaders(token)}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const getSingle = (type: string, token:string, id:number) => {
    return axios({
        method: 'get',
        url: `/api/${type}s/${id}`,
        headers: {...jsonHeaders(), ...authHeaders(token)}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const create = (type: string, token:string, data:IBody) => {
    return axios({
        method: 'post',
        url: `/api/${type}s`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
       data,
    }).then(response => {

        return response
    })
        .catch(err => parseError(err))
}

export const edit = (type: string, token:string, data: IBody) => {
    return axios({
        method: 'put',
        url: `/api/${type}s/id`,
        headers: {...jsonHeaders(), ...authHeaders(token)},
        data
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}


