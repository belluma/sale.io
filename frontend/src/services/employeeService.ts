import axios from "axios";
import {parseError} from './errorService';


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
