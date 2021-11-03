import {createAsyncThunk, createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {getErrorMessage} from "./errorSlice";
import {getAllEmployees} from "../services/employeeService";
import { IResponseGetAllEmployees} from "../interfaces/IApiResponse";
import { IEmployeeState } from '../interfaces/IStates';


const initialState:IEmployeeState = {
    employees: [],
    currentEmployee: undefined,
}

export const getEmployees = createAsyncThunk(
    'login',
    async (_, thunkAPI) => {
        console.log(123)
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



export const selectEmployees = (state: RootState) => state.employee.employees;
export const selectCurrentEmployee = (state: RootState) => state.employee.currentEmployee;

export default employeeSlice.reducer;
