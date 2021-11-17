import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {IOrdersState} from '../interfaces/IStates';
import {IResponseGetAllOrders, IResponseGetOneOrder} from "../interfaces/IApiResponse";
import {
    getAll as apiGetAll,
    getOne as apiGetOne,
    create as apiCreate,
    edit as apiEdit,
    del as apiDelete,
    receiveOrder as apiReceiveOrder
} from '../services/apiService'
import {emptyOrder, IEditOrderItem, IOrder, IOrderItem} from "../interfaces/IOrder";
import {ISupplier} from "../interfaces/ISupplier";
import {
    handleError,
    invalidDataError,
    setPending,
    stopPendingAndHandleError,
    handleApiResponse
} from "./errorHelper";
import {hideDetails} from "./detailsSlice";


const initialState: IOrdersState = {
    orders: [],
    current: emptyOrder,
    pending: false,
    success: false, toSave: emptyOrder,
}
const route = "orders_suppliers";
export const validateOrder = (order: IOrder): boolean => {
    if (!order.orderItems.length || !order.supplier) return false;
    return order.orderItems.every(item => {
        return item.product &&
            item.product.suppliers?.some(s => s.id === order.supplier?.id)
    });
}
const validateBeforeSendingToBackend = ({order}: RootState): boolean => {
    return validateOrder(order.toSave);
}

const hideDetailsAndReloadList = (dispatch: Dispatch) => {
    dispatch(hideDetails());
    //@ts-ignore
    dispatch(getAllOrders());
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
        const {data, status, statusText} = await apiCreate(route, token, getState().order.toSave);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch)
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
        if (status === 200) hideDetailsAndReloadList(dispatch)
        return {data, status, statusText}
    }
)

export const deleteOrder = createAsyncThunk<IResponseGetOneOrder, string, { state: RootState, dispatch: Dispatch }>(
    'orders/delete',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiDelete(route, token, id);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch)
        return {data, status, statusText}
    }
)

export const receiveOrder = createAsyncThunk<IResponseGetOneOrder, IOrder, { state: RootState, dispatch: Dispatch }>(
    'orders/receive',
    async (order, {getState, dispatch}) => {
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiReceiveOrder(token, order);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch)
        console.log(data)
        return {data, status, statusText}
    }
)

export const orderSlice = createSlice({
    name: 'order',
    initialState,
    reducers: {
        chooseCurrentOrder: (state, {payload}: PayloadAction<string>) => {
            state.current = state.orders.find(order => order.id?.toString() === payload) || emptyOrder;
        },
        chooseSupplier: ({toSave}: IOrdersState, {payload}: PayloadAction<ISupplier>) => {
            toSave.supplier = payload;
        },
        addProductToOrder: ({toSave}: IOrdersState, {payload}: PayloadAction<IOrderItem>) => {
            const {orderItems} = toSave;
            const index = orderItems.map(item => item.product?.id).indexOf(payload.product?.id);
            if (index >= 0) {
                //@ts-ignore orderItems[index] can't be undefined through line above, quantity can't be undefined through form validation
                const itemWithNewQty = {...orderItems[index], quantity: orderItems[index].quantity + payload?.quantity}
                orderItems.splice(index, 1, itemWithNewQty)
                return
            }
            toSave.orderItems = [...orderItems, payload]
        },
        editItemQty: ({toSave}: IOrdersState, {payload}: PayloadAction<IEditOrderItem>) => {
            toSave.orderItems[payload.index].quantity = payload.quantity;
        }
        ,
        removeOrderItem: ({toSave}: IOrdersState, {payload}: PayloadAction<number>) => {
            toSave.orderItems.splice(payload, 1);
        },
        handleOrderFormInput: (state: IOrdersState, {payload}: PayloadAction<IOrder>) => {
            state.toSave = payload;
        },
        closeSuccess: (state: IOrdersState) => {
            state.success = false
        }
    },
    extraReducers: (builder => {
        builder
            .addCase(getAllOrders.pending, setPending)
            .addCase(getOneOrder.pending, setPending)
            .addCase(createOrder.pending, setPending)
            .addCase(editOrder.pending, setPending)
            .addCase(deleteOrder.pending, setPending)
            .addCase(receiveOrder.pending, setPending)
            .addCase(getAllOrders.fulfilled, (state, action: PayloadAction<IResponseGetAllOrders>) => {
                if (stopPendingAndHandleError(state, action, emptyOrder)) return;
                state.orders = action.payload.data;
            })
            .addCase(getOneOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneOrder>) => {
                stopPendingAndHandleError(state, action, emptyOrder);
            })
            .addCase(createOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneOrder>) => {
                handleApiResponse(state, action, emptyOrder);
            })
            .addCase(editOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneOrder>) => {
                handleApiResponse(state, action, emptyOrder);
            })
            .addCase(deleteOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneOrder>) => {
                handleApiResponse(state, action, emptyOrder);
            })
            .addCase(receiveOrder.fulfilled, (state, action: PayloadAction<IResponseGetOneOrder>) => {
                handleApiResponse(state, action, emptyOrder);
            })
    })
})

export const {
    chooseCurrentOrder,
    handleOrderFormInput,
    addProductToOrder,
    chooseSupplier,
    removeOrderItem,
    editItemQty,
    closeSuccess
} = orderSlice.actions;

export const selectOrders = (state: RootState) => state.order.orders;
export const selectCurrentOrder = (state: RootState) => state.order.current;
export const selectOrderToSave = (state: RootState) => state.order.toSave;
export const selectOrderPending = (state: RootState) => state.order.pending;
export const selectOrderSuccess = (state: RootState) => state.order.success;


export default orderSlice.reducer;
