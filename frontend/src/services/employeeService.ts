import axios from "axios";
import {parseError} from './errorService';
import {ICredentials, IEmployee} from "../interfaces/IEmployee";
import {jsonHeaders} from "./serviceUtils";


export const getAllEmployees = () => {
    return axios({
        method: 'get',
        url: `/api/employee`,
        headers: jsonHeaders(),
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}


export const extractCredentials = (employee:IEmployee):ICredentials => {
    const {email, phone, picture, ...credentials} = employee;
    return credentials;
}
