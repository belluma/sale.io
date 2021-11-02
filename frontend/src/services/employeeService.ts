import axios from "axios";
import {parseError} from './errorService';


export const getAllEmployees= () => {
    return axios({
        method: 'get',
        url: `/api/employees`,
        headers: {}
    }).then(response => {
        return response
    })
        .catch(err => parseError(err))
}
