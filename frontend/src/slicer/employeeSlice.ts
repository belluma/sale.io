import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {extractCredentials, getAllEmployees} from "../services/employeeService";
import {IResponseGetAllEmployees} from "../interfaces/IApiResponse";
import {IEmployeeState} from '../interfaces/IStates';
import {handleError, setPending, stopPendingAndHandleError} from "./errorHelper";
import {emptyEmployee} from "../interfaces/IEmployee";


const initialState: IEmployeeState = {
    employees: [],
    current: undefined,
    currentEmployeeCredentials: undefined,
    pending: false,
    success: false,
    toSave: emptyEmployee,
}

export const getEmployees = createAsyncThunk<IResponseGetAllEmployees, void, { dispatch:Dispatch }>(
    'employees/getAll',
    async (_, {dispatch}) => {
        const {data, status, statusText} = await getAllEmployees();
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)


export const employeeSlice = createSlice({
    name: 'employee',
    initialState,
    reducers: {
        chooseCurrentEmployee: (state, action: PayloadAction<string>) => {
            const employee = state.employees.filter(e => e.username === action.payload)[0];
            state.current = employee
            state.currentEmployeeCredentials = extractCredentials(employee);
        },
        toBeReplaced: (state) => {console.log("I have to stay here until all view are implemented")}
    },
    extraReducers: (builder => {
        builder.addCase(getEmployees.pending,setPending)
        builder.addCase(getEmployees.fulfilled, (state, action: PayloadAction<IResponseGetAllEmployees>) => {
            stopPendingAndHandleError(state, action, emptyEmployee);
            state.employees = action.payload.data;
        })
    })
})


export const {chooseCurrentEmployee, toBeReplaced} = employeeSlice.actions;

export const selectEmployees = (state: RootState) => state.employee.employees;
export const selectCurrentEmployee = (state: RootState) => state.employee.current;
export const selectCurrentEmployeeCredentials = (state: RootState) => state.employee.currentEmployeeCredentials;
export const selectEmployeePending = (state: RootState) => state.employee.pending;
export const selectEmployeeSuccess = (state: RootState) => state.employee.success;

export default employeeSlice.reducer;
