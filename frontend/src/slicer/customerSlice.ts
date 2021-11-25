import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {ICustomersState} from '../interfaces/IStates';
import {IResponseGetAllCustomers, IResponseGetOneCustomer} from "../interfaces/IApiResponse";
import {
    getAll as apiGetAll,
    getOne as apiGetOne,
    create as apiCreate,
    del as apiDelete,
    addItemsToOrder as apiAddItemsToOrder,
    takeItemsOffOrder as apiTakeItemsOffOrder
} from '../services/apiService'
import {emptyCustomer, ICustomer} from "../interfaces/ICustomer";
import {hideDetails} from "./detailsSlice";
import {
    setPending,
    stopPendingAndHandleError,
    handleError,
    handleApiResponse,
} from "./errorHelper";
import {IOrderItem} from "../interfaces/IOrder";


const initialState: ICustomersState = {
    customers: [],
    current: emptyCustomer,
    pending: false,
    success: false,
    toSave: emptyCustomer
}
const route = "orders_customers";


const hideDetailsAndReloadList = (dispatch: Dispatch) => {
    dispatch(hideDetails());
    //@ts-ignore
    dispatch(getAllOpenCustomers());
}

export const getAllOpenCustomers = createAsyncThunk<IResponseGetAllCustomers, void, { state: RootState, dispatch: Dispatch }>(
    'customers/getAll',
    async (_, {getState, dispatch}) => {
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiGetAll(route + '/all', token);
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
        const token = getState().authentication.token
        const {data, status, statusText} = await apiCreate(route, token, getState().customer.toSave);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch);
        return {data, status, statusText}
    }
)

export const addItemsToOrder = createAsyncThunk<IResponseGetOneCustomer,IOrderItem, { state: RootState, dispatch: Dispatch }>(
    'customers/add',
    async (orderItem, {getState, dispatch}) => {
        const customer = getState().customer.current;
        const order = {order: customer, itemToAddOrRemove: orderItem}
        const token = getState().authentication.token
        const {data, status, statusText} = await apiAddItemsToOrder( token, order);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch)
        return {data, status, statusText}
    }
)
export const takeItemsOffOrder = createAsyncThunk<IResponseGetOneCustomer,IOrderItem, { state: RootState, dispatch: Dispatch }>(
    'customers/remove',
    async (orderItem, {getState, dispatch}) => {
        const customer = getState().customer.current;
        const order = {order: customer, itemToAddOrRemove: orderItem}
        const token = getState().authentication.token
        const {data, status, statusText} = await apiTakeItemsOffOrder( token, order);
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
            .addCase(getAllOpenCustomers.pending, setPending)
            .addCase(getOneCustomer.pending, setPending)
            .addCase(createCustomer.pending, setPending)
            .addCase(addItemsToOrder.pending, setPending)
            .addCase(takeItemsOffOrder.pending, setPending)
            .addCase(deleteCustomer.pending, setPending)
            .addCase(getAllOpenCustomers.fulfilled, (state, action: PayloadAction<IResponseGetAllCustomers>) => {
                if (stopPendingAndHandleError(state, action, emptyCustomer)) return;
                state.customers = action.payload.data;
            })
            .addCase(getOneCustomer.fulfilled, (state, action: PayloadAction<IResponseGetOneCustomer>) => {
                stopPendingAndHandleError(state, action, emptyCustomer);
            })
            .addCase(createCustomer.fulfilled, (state, action: PayloadAction<IResponseGetOneCustomer>) => {
                handleApiResponse(state, action, emptyCustomer)
                console.log(action)
            })
            .addCase(addItemsToOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneCustomer>) => {
                handleApiResponse(state, action, emptyCustomer)
            })
            .addCase(takeItemsOffOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneCustomer>) => {
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
