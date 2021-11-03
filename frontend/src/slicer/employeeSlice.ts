import {createAsyncThunk, createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {getErrorMessage} from "./errorSlice";
import {getAllEmployees} from "../services/employeeService";
import { IResponseGetAllEmployees} from "../interfaces/IApiResponse";
import { IEmployeeState } from '../interfaces/IStates';
import { IEmployee } from '../interfaces/IEmployee';


const initialState:IEmployeeState = {
    employees: [],
    currentEmployee: undefined,
    pending: false,
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
    reducers: {
        chooseCurrentEmployee:(state, action:PayloadAction<IEmployee>) => {
            state.currentEmployee = action.payload;
        }
    },
    extraReducers: (builder => {
        builder.addCase(getEmployees.pending, state => {
            state.pending = true;
        })
        builder.addCase(getEmployees.fulfilled, (state, action: PayloadAction<IResponseGetAllEmployees>) => {
            if (action.payload.status !== 200) {
                return;
            }
            state.pending = false;
            state.employees = action.payload.data;
        })
    })
})


export const {chooseCurrentEmployee}= employeeSlice.actions;

export const selectEmployees = (state: RootState) => state.employee.employees;
export const selectCurrentEmployee = (state: RootState) => state.employee.currentEmployee;
export const selectPending = (state:RootState) => state.employee.pending;

export default employeeSlice.reducer;
