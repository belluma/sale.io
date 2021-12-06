import {Action, createAsyncThunk, createSlice, Dispatch, PayloadAction, ThunkDispatch} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {extractCredentials, getAllEmployees} from "../services/employeeService";
import {IResponseGetAllEmployees, IResponseGetOneEmployee, IResponseGetOneProduct} from "../interfaces/IApiResponse";
import {IEmployeeState} from '../interfaces/IStates';
import {handleApiResponse, handleError, invalidDataError, setPending, stopPendingAndHandleError} from "./errorHelper";
import {emptyEmployee, IEmployee} from "../interfaces/IEmployee";
import {create as apiCreate} from '../services/apiService'
import {hideDetails} from "./detailsSlice";
import {getAllProducts, validateProduct} from "./productSlice";

const initialState: IEmployeeState = {
    employees: [],
    current: emptyEmployee,
    currentEmployeeCredentials: undefined,
    pending: false,
    success: false,
    toSave: emptyEmployee,
}

const route = 'employees';

export const validateEmployee = (employee: IEmployee): boolean => {
    const name = !!employee.firstName?.length || !!employee.lastName?.length;
    const contact = !!employee.email?.length || !!employee.phone?.length;
    const passwordStrength = employee.password?.length > 3;
    return name && contact && passwordStrength;
}
const validateBeforeSendingToBackend = ({employee}: RootState) => {
    return validateEmployee(employee.toSave);
}
const hideDetailsAndReloadList = (dispatch: ThunkDispatch<RootState, void, Action>) => {
    dispatch(hideDetails());
    dispatch(getEmployees());
}


export const getEmployees = createAsyncThunk<IResponseGetAllEmployees, void, {state:RootState, dispatch: Dispatch }>(
    'employees/getAll',
    async (_, {getState, dispatch}) => {
       const {data, status, statusText} = await getAllEmployees();
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const saveEmployee = createAsyncThunk<IResponseGetOneEmployee, void, { state: RootState, dispatch: Dispatch }>(
    'employees/create',
    async (_, {getState, dispatch}) => {
        if (!validateBeforeSendingToBackend(getState())) {
            return invalidDataError;
        }
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiCreate(route, token, getState().employee.toSave);
        handleError(status, statusText, dispatch);
        if(status === 200) hideDetailsAndReloadList(dispatch);
        return {data, status, statusText}
    }
)

export const employeeSlice = createSlice({
    name: 'employee',
    initialState,
    reducers: {
        chooseCurrentEmployee: (state, action: PayloadAction<string>) => {
            const employee = state.employees.find(e => e.username === action.payload) || emptyEmployee;
            state.current = employee
            state.currentEmployeeCredentials = extractCredentials(employee);
        },
        handleEmployeeFormInput: (state, {payload}: PayloadAction<IEmployee>) => {
            state.toSave = payload;
        },
        toBeReplaced: (state) => {
            console.log("I have to stay here until all view are implemented")
        },
        closeSuccess: (state: IEmployeeState) => {
            state.success = false
        }
    },
    extraReducers: (builder => {
        builder.addCase(getEmployees.pending, setPending)
        builder.addCase(saveEmployee.pending, setPending)
        builder.addCase(getEmployees.fulfilled, (state, action: PayloadAction<IResponseGetAllEmployees>) => {
            stopPendingAndHandleError(state, action, emptyEmployee);
            state.employees = action.payload.data;
        })
        builder.addCase(saveEmployee.fulfilled, (state, action: PayloadAction<IResponseGetOneEmployee>) => {
            handleApiResponse(state, action, emptyEmployee);
        })
    })
})


export const {chooseCurrentEmployee, toBeReplaced, closeSuccess, handleEmployeeFormInput} = employeeSlice.actions;

export const selectEmployees = (state: RootState) => state.employee.employees;
export const selectCurrentEmployee = (state: RootState) => state.employee.current;
export const selectCurrentEmployeeCredentials = (state: RootState) => state.employee.currentEmployeeCredentials;
export const selectEmployeeToSave = (state: RootState) => state.employee.toSave;
export const selectEmployeePending = (state: RootState) => state.employee.pending;
export const selectEmployeeSuccess = (state: RootState) => state.employee.success;

export default employeeSlice.reducer;
