import axios from "axios";
import {ICredentials} from "../interfaces/IEmployee";
import {parseError} from './errorService';


const parseJwt = (token: string) => {
    try {
        return JSON.parse(atob(token.split(".")[1]));
    } catch (e) {
        return null;
    }
}

export const validateToken = (token: string): boolean => {
    const decodedJwt = parseJwt(token);
    if (!decodedJwt) return false;
    return decodedJwt.exp * 1000 > Date.now()
}

export const sendLoginData = (credentials: ICredentials) => {
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





