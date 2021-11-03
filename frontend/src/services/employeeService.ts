import axios from "axios";
import {parseError} from './errorService';
import {ICredentials, IEmployee} from "../interfaces/IEmployee";


export const getAllEmployees = () => {
    return axios({
        method: 'get',
        url: `/api/employee`,
        headers: {"Content-Type": "application/json"}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}


export const extractCredentials = (employee:IEmployee):ICredentials => {
    const {email, phone, picture, ...credentials} = employee;
    return credentials;
}
