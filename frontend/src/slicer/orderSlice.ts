import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {IOrdersState} from '../interfaces/IStates';
import {IResponseGetAllOrders} from "../interfaces/IApiResponse";
import {
    getAll as apiGetAll,
    getOne as apiGetOne,
    create as apiCreate,
    edit as apiEdit,
    del as apiDelete
} from '../services/apiService'
import {IOrder} from "../interfaces/IOrder";
import {handleError, invalidDataError} from "./helper";


const initialState: IOrdersState = {
    orders: [],
    currentOrder: undefined,
    pending: false,
    orderToSave: {},
}
const validateOrder = (state: RootState) => {
    const necessaryValues = ['name', 'suppliers', 'category', 'purchasePrice', 'unitSize']
    const setValues = Object.keys(state.order.orderToSave)
    return setValues.every(v => necessaryValues.indexOf(v) >= 0);
}


export const getAllOrders = createAsyncThunk<IResponseGetAllOrders, void, { state: RootState, dispatch: Dispatch }>(
    'getAll',
    async (_, {getState, dispatch}) => {
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiGetAll("order", token);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const getOneOrder = createAsyncThunk<IResponseGetAllOrders, string, { state: RootState, dispatch: Dispatch }>(
    'getOne',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiGetOne("order", token, id);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const createOrder = createAsyncThunk<IResponseGetAllOrders, void, { state: RootState, dispatch: Dispatch }>(
    'create',
    async (_, {getState, dispatch}) => {
        if (!validateOrder(getState())) {
//handleError here
            return invalidDataError;
        }
        const token = getState().authentication.token
        const {data, status, statusText} = await apiCreate("order", token, getState().order.orderToSave);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const editOrder = createAsyncThunk<IResponseGetAllOrders, IOrder, { state: RootState, dispatch: Dispatch }>(
    'edit',
    async (order, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiEdit("order", token, order);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const deleteOrder = createAsyncThunk<IResponseGetAllOrders, string, { state: RootState, dispatch: Dispatch }>(
    'delete',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiDelete("order", token, id);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const orderSlice = createSlice({
    name: 'order',
    initialState,
    reducers: {
        chooseCurrentOrder: (state, action: PayloadAction<string>) => {
            state.currentOrder = state.orders.filter(p => p.id === action.payload)[0];
        },
        handleOrderFormInput: (state, {payload}: PayloadAction<IOrder>) => {
            state.orderToSave = payload;
        },
    },
    extraReducers: (builder => {
        const setPending = (state: IOrdersState) => {
            state.pending = true;
        }
        const stopPendingAndHandleError = (state: IOrdersState, action: PayloadAction<IResponseGetAllOrders>) => {
            state.pending = false;
            return action.payload.status !== 200;
        }
        builder
            .addCase(getAllOrders.pending, setPending)
            .addCase(getOneOrder.pending, setPending)
            .addCase(createOrder.pending, setPending)
            .addCase(editOrder.pending, setPending)
            .addCase(deleteOrder.pending, setPending)
            .addCase(getAllOrders.fulfilled, (state, action: PayloadAction<IResponseGetAllOrders>) => {
                if (stopPendingAndHandleError(state, action)) return;
                state.orders = action.payload.data;
            })
            .addCase(getOneOrder.fulfilled, (state, action: PayloadAction<IResponseGetAllOrders>) => {
                stopPendingAndHandleError(state, action);
            })
            .addCase(createOrder.fulfilled, (state, action: PayloadAction<IResponseGetAllOrders>) => {
                stopPendingAndHandleError(state, action);
            })
            .addCase(editOrder.fulfilled, (state, action: PayloadAction<IResponseGetAllOrders>) => {
                stopPendingAndHandleError(state, action);
            })
            .addCase(deleteOrder.fulfilled, (state, action: PayloadAction<IResponseGetAllOrders>) => {
                stopPendingAndHandleError(state, action);
            })
    })
})

export const {chooseCurrentOrder, handleOrderFormInput} = orderSlice.actions;

export const selectOrders = (state: RootState) => state.order.orders;
export const selectCurrentOrder = (state: RootState) => state.order.currentOrder;
export const selectOrderToSave = (state: RootState) => state.order.orderToSave;
export const selectPending = (state: RootState) => state.order.pending;


export default orderSlice.reducer;
