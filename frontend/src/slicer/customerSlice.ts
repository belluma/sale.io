import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {ICustomersState} from '../interfaces/IStates';
import {IResponseGetAllCustomers, IResponseGetOneCustomer} from "../interfaces/IApiResponse";
import {
    getAll as apiGetAll,
    getOne as apiGetOne,
    create as apiCreate,
    edit as apiEdit,
    del as apiDelete
} from '../services/apiService'
import {emptyCustomer, ICustomer} from "../interfaces/ICustomer";
import {hideDetails} from "./detailsSlice";
import {
    setPending,
    stopPendingAndHandleError,
    handleError,
    invalidDataError,
    handleApiResponse,
} from "./errorHelper";


const initialState: ICustomersState = {
    customers: [],
    current: emptyCustomer,
    pending: false,
    success: false,
    toSave: emptyCustomer
}
const route = "customers";

export const validateCustomer = (customer: ICustomer): boolean => {
    const necessaryValues = ['name', 'suppliers', 'purchasePrice', 'unitSize']
    //@ts-ignore values defined in line above must be keys of ICustomer
    if (necessaryValues.every(v => !!customer[v])) {
        //@ts-ignore line above checks that values are not undefined
        return customer.name.length > 0 && customer.suppliers.length > 0 && customer.purchasePrice > 0 && customer.unitSize > 0
    }
    return false
}

const validateBeforeSendingToBackend = ({customer}: RootState) => {
    return validateCustomer(customer.toSave);
}

const hideDetailsAndReloadList = (dispatch: Dispatch) => {
    dispatch(hideDetails());
    //@ts-ignore
    dispatch(getAllCustomers());
}

export const getAllCustomers = createAsyncThunk<IResponseGetAllCustomers, void, { state: RootState, dispatch: Dispatch }>(
    'customers/getAll',
    async (_, {getState, dispatch}) => {
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiGetAll(route, token);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const getOneCustomer = createAsyncThunk<IResponseGetOneCustomer, string, { state: RootState, dispatch: Dispatch }>(
    'customers/getOne',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiGetOne(route, token, id);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const createCustomer = createAsyncThunk<IResponseGetOneCustomer, void, { state: RootState, dispatch: Dispatch }>(
    'customers/create',
    async (_, {getState, dispatch}) => {
        if (!validateBeforeSendingToBackend(getState())) {
            return invalidDataError;
        }
        const token = getState().authentication.token
        const {data, status, statusText} = await apiCreate(route, token, getState().customer.toSave);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch);
        return {data, status, statusText}
    }
)

export const editCustomer = createAsyncThunk<IResponseGetOneCustomer, ICustomer, { state: RootState, dispatch: Dispatch }>(
    'customers/edit',
    async (customer, {getState, dispatch}) => {
        if (!validateBeforeSendingToBackend(getState())) {
            return invalidDataError;
        }
        const token = getState().authentication.token
        const {data, status, statusText} = await apiEdit(route, token, customer);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch)
        return {data, status, statusText}
    }
)

export const deleteCustomer = createAsyncThunk<IResponseGetOneCustomer, string, { state: RootState, dispatch: Dispatch }>(
    'customers/delete',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiDelete(route, token, id);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch)
        return {data, status, statusText}
    }
)

export const customerSlice = createSlice({
    name: 'customer',
    initialState,
    reducers: {
        chooseCurrentCustomer: (state, action: PayloadAction<string>) => {
            state.current = state.customers.find(customer => customer.id?.toString() === action.payload) || emptyCustomer;
        },
        handleCustomerFormInput: (state, {payload}: PayloadAction<ICustomer>) => {
            state.toSave = payload;
        },
        closeSuccess: (state: ICustomersState) => {
            state.success = false
        }
    },
    extraReducers: (builder => {
        builder
            .addCase(getAllCustomers.pending, setPending)
            .addCase(getOneCustomer.pending, setPending)
            .addCase(createCustomer.pending, setPending)
            .addCase(editCustomer.pending, setPending)
            .addCase(deleteCustomer.pending, setPending)
            .addCase(getAllCustomers.fulfilled, (state, action: PayloadAction<IResponseGetAllCustomers>) => {
                if (stopPendingAndHandleError(state, action, emptyCustomer)) return;
                state.customers = action.payload.data;
            })
            .addCase(getOneCustomer.fulfilled, (state, action: PayloadAction<IResponseGetOneCustomer>) => {
                stopPendingAndHandleError(state, action, emptyCustomer);
            })
            .addCase(createCustomer.fulfilled, (state, action: PayloadAction<IResponseGetOneCustomer>) => {
                handleApiResponse(state, action, emptyCustomer)
            })
            .addCase(editCustomer.fulfilled, (state, action: PayloadAction<IResponseGetOneCustomer>) => {
                handleApiResponse(state, action, emptyCustomer)
            })
            .addCase(deleteCustomer.fulfilled, (state, action: PayloadAction<IResponseGetOneCustomer>) => {
                handleApiResponse(state, action, emptyCustomer)
            })
    })
})

export const {chooseCurrentCustomer, handleCustomerFormInput, closeSuccess} = customerSlice.actions;

export const selectCustomers = (state: RootState) => state.customer.customers;
export const selectCurrentCustomer = (state: RootState) => state.customer.current;
export const selectCustomerToSave = (state: RootState) => state.customer.toSave;
export const selectCustomerPending = (state: RootState) => state.customer.pending;
export const selectCustomerSuccess = (state: RootState) => state.customer.success;


export default customerSlice.reducer;
