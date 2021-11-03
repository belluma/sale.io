import axios from "axios";
import {ICredentials} from "../interfaces/IEmployee";
import {parseError} from './errorService';

export const sendLoginData = (credentials: ICredentials) => {
    console.log(123)
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

export const registerAdmin = (credentials: ICredentials) => {
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





