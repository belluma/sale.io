import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {IOrdersState} from '../interfaces/IStates';
import {IResponseGetAllOrders, IResponseGetOneOrder} from "../interfaces/IApiResponse";
import {
    getAll as apiGetAll,
    getOne as apiGetOne,
    create as apiCreate,
    edit as apiEdit,
    del as apiDelete
} from '../services/apiService'
import {IEditOrderItem, IOrder, IOrderItem} from "../interfaces/IOrder";
import {handleError, invalidDataError} from "./errorHelper";
import {ISupplier} from "../interfaces/ISupplier";


const initialState: IOrdersState = {
    orders: [],
    currentOrder: undefined,
    pending: false,
    orderToSave: {
        orderItems: []
    },
}
const route = "orders_suppliers";
export const validateOrder = (order: IOrder): boolean => {
    if (!order.orderItems.length || !order.supplier) return false;
    return order.orderItems.every(i => {
        return i.product &&
            i.product.suppliers?.some(s => s.id === order.supplier?.id)
    });
}
const validateBeforeSendingToBackend = ({order}: RootState): boolean => {
    return validateOrder(order.orderToSave);
}


export const getAllOrders = createAsyncThunk<IResponseGetAllOrders, void, { state: RootState, dispatch: Dispatch }>(
    'orders/getAll',
    async (_, {getState, dispatch}) => {
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiGetAll(route, token);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const getOneOrder = createAsyncThunk<IResponseGetOneOrder, string, { state: RootState, dispatch: Dispatch }>(
    'orders/getOne',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiGetOne(route, token, id);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const createOrder = createAsyncThunk<IResponseGetOneOrder, void, { state: RootState, dispatch: Dispatch }>(
    'orders/create',
    async (_, {getState, dispatch}) => {
        if (!validateBeforeSendingToBackend(getState())) {
            return invalidDataError;
        }
        const token = getState().authentication.token
        const {data, status, statusText} = await apiCreate(route, token, getState().order.orderToSave);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const editOrder = createAsyncThunk<IResponseGetOneOrder, IOrder, { state: RootState, dispatch: Dispatch }>(
    'orders/edit',
    async (order, {getState, dispatch}) => {
        if (!validateBeforeSendingToBackend(getState())) {
            return invalidDataError;
        }
        const token = getState().authentication.token
        const {data, status, statusText} = await apiEdit(route, token, order);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const deleteOrder = createAsyncThunk<IResponseGetOneOrder, string, { state: RootState, dispatch: Dispatch }>(
    'orders/delete',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiDelete(route, token, id);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const orderSlice = createSlice({
    name: 'order',
    initialState,
    reducers: {
        chooseCurrentOrder: (state, {payload}: PayloadAction<string>) => {
            state.currentOrder = state.orders.filter(p => p.id === payload)[0];
        },
        chooseSupplier: ({orderToSave}, {payload}: PayloadAction<ISupplier>) => {
            orderToSave.supplier = payload;
        },
        addProductToOrder: ({orderToSave}, {payload}: PayloadAction<IOrderItem>) => {
            const {orderItems} = orderToSave;
            const index = orderItems.map(i => i.product?.id).indexOf(payload.product?.id);
            if (index >= 0) {
                //@ts-ignore orderItems[index] can't be undefined through line above, quantity can't be undefined through form validation
                const itemWithNewQty = {...orderItems[index], quantity: orderItems[index].quantity + payload?.quantity}
                orderItems.splice(index, 1, itemWithNewQty)
                return
            }
            orderToSave.orderItems = [...orderItems, payload]
        },
        editItemQty: ({orderToSave}, {payload}: PayloadAction<IEditOrderItem>) => {
            orderToSave.orderItems[payload.index].quantity = payload.quantity;
        }
        ,
        removeOrderItem: ({orderToSave}, {payload}: PayloadAction<number>) => {
            orderToSave.orderItems.splice(payload, 1);
        },
        handleOrderFormInput: (state, {payload}: PayloadAction<IOrder>) => {
            state.orderToSave = payload;
        },
    },
    extraReducers: (builder => {
        const setPending = (state: IOrdersState) => {
            state.pending = true;
        }
        const stopPendingAndHandleError = (state: IOrdersState, action: PayloadAction<IResponseGetAllOrders> | PayloadAction<IResponseGetOneOrder>) => {
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
            .addCase(getOneOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneOrder>) => {
                stopPendingAndHandleError(state, action);
            })
            .addCase(createOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneOrder>) => {
                console.log(action)
                stopPendingAndHandleError(state, action);
            })
            .addCase(editOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneOrder>) => {
                stopPendingAndHandleError(state, action);
            })
            .addCase(deleteOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneOrder>) => {
                stopPendingAndHandleError(state, action);
            })
    })
})

export const {
    chooseCurrentOrder,
    handleOrderFormInput,
    addProductToOrder,
    chooseSupplier,
    removeOrderItem,
    editItemQty
} = orderSlice.actions;

export const selectOrders = (state: RootState) => state.order.orders;
export const selectCurrentOrder = (state: RootState) => state.order.currentOrder;
export const selectOrderToSave = (state: RootState) => state.order.orderToSave;
export const selectPending = (state: RootState) => state.order.pending;


export default orderSlice.reducer;
