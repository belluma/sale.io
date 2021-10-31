import axios from "axios";
import { ICredentials } from "../interfaces/IEmployee";


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
        .catch(err => {
            return {data: "", status: err.response.status, statusText: err.response.data.message}
        })
}

export const getGithubClientId = () => {
    return axios({
        method: 'get',
        url: `/auth/github/client_id`
    }).then(response => response)
        .catch(err => parseError(err))
}

export const sendLoginDataToGithub = (code: string) => {
    return axios({
        method: 'get',
        url: `/auth/github/${code}`,

    })
        .then(response => response)
        .catch(err => parseError(err))
}


function parseError(err: any) {
    return {data: "", status: err.response.status, statusText: err.response.data.message}
}
