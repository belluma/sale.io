import {createAsyncThunk, createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {ICredentials, IEmployee} from '../interfaces/IEmployee'
import {sendLoginData} from "../services/authService";
import {getErrorMessage} from "./errorSlice";
import {getAllEmployees} from "../services/employeeService";
import {IResponseData, IResponseGetAllEmployees} from "../interfaces/IApiResponse";
import { IEmployeeState } from '../interfaces/IStates';


const initialState:IEmployeeState = {
    employees: [],
    currentEmployee: undefined,
}

export const getEmployees = createAsyncThunk(
    'login',
    async (_, thunkAPI) => {
        const {data, status, statusText} = await getAllEmployees();
        if (status !== 200) {
            thunkAPI.dispatch(getErrorMessage({status, statusText}))
        }
        return {data, status, statusText}
    }
)


export const employeeSlice = createSlice({
    name: 'employee',
    initialState,
    reducers: {},
    extraReducers: (builder => {
        builder.addCase(getEmployees.fulfilled, (state, action: PayloadAction<IResponseGetAllEmployees>) => {
            if (action.payload.status !== 200) {
                return;
            }
            state.employees = action.payload.data;
        })
    })
})


export default employeeSlice.reducer;
