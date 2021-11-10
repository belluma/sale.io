import axios from "axios";
import {IUserCredentials} from "../interfaces/IEmployee";
import {parseError} from './errorService';

export const sendLoginData = (credentials: IUserCredentials) => {
    return axios({
        method: 'post',
        url: `/auth/login`,
        data: credentials,
        headers: {}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}

export const registerAdmin = (credentials: IUserCredentials) => {
    return axios({
        method: 'post',
        url: `/auth/signup`,
        data: credentials,
        headers: {}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}





