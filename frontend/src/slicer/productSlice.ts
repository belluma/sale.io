import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import { IProductsState} from '../interfaces/IStates';
import {getErrorMessage} from "./errorSlice";
import {IResponseGetAllProducts} from "../interfaces/IApiResponse";
import {
    getAll as apiGetAll,
    getOne as apiGetOne,
    create as apiCreate,
    edit as apiEdit,
    del as apiDelete
} from '../services/apiService'
import {IProduct} from "../interfaces/IProduct";


const initialState: IProductsState = {
    products: [],
    currentProduct: undefined,
    pending: false,
}
export const handleError = (status:number, statusText: string, dispatch: Dispatch) => {
    if (status !== 200) {
        dispatch(getErrorMessage({status, statusText}))
    }
}


export const getAllProducts = createAsyncThunk<IResponseGetAllProducts, void, { state: RootState, dispatch: Dispatch }>(
    'getAll',
    async (_, {getState, dispatch}) => {
        console.log(Date.now());
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiGetAll("product", token);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const getOneProduct = createAsyncThunk<IResponseGetAllProducts, string, { state: RootState, dispatch: Dispatch }>(
    'getOne',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiGetOne("product", token, id);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const createProduct = createAsyncThunk<IResponseGetAllProducts, IProduct, { state: RootState, dispatch: Dispatch }>(
    'create',
    async (product, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiCreate("product", token, product);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const editProduct = createAsyncThunk<IResponseGetAllProducts, IProduct, { state: RootState, dispatch: Dispatch }>(
    'edit',
    async (product, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiEdit("product", token, product);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const deleteProduct = createAsyncThunk<IResponseGetAllProducts, string, { state: RootState, dispatch: Dispatch }>(
    'delete',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiDelete("product", token, id);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const productSlice = createSlice({
    name: 'product',
    initialState,
    reducers: {
        chooseCurrentProduct: (state, action: PayloadAction<string>) => {
            state.currentProduct = state.products.filter(p => p.id === action.payload)[0];
        }
    },
    extraReducers: (builder => {
        const setPending = (state: IProductsState) => {
            state.pending = true;
        }
        const stopPendingAndHandleError = (state: IProductsState, action: PayloadAction<IResponseGetAllProducts>) => {
            state.pending = false;
            return action.payload.status !== 200;
        }
        builder
            .addCase(getAllProducts.pending, setPending)
            .addCase(getOneProduct.pending, setPending)
            .addCase(createProduct.pending, setPending)
            .addCase(editProduct.pending, setPending)
            .addCase(deleteProduct.pending, setPending)
            .addCase(getAllProducts.fulfilled, (state, action: PayloadAction<IResponseGetAllProducts>) => {
                if (stopPendingAndHandleError(state, action)) return;
                state.products = action.payload.data;
            })
            .addCase(getOneProduct.fulfilled, (state, action: PayloadAction<IResponseGetAllProducts>) => {
                stopPendingAndHandleError(state, action);
            })
            .addCase(createProduct.fulfilled, (state, action: PayloadAction<IResponseGetAllProducts>) => {
                stopPendingAndHandleError(state, action);
            })
            .addCase(editProduct.fulfilled, (state, action: PayloadAction<IResponseGetAllProducts>) => {
                stopPendingAndHandleError(state, action);
            })
            .addCase(deleteProduct.fulfilled, (state, action: PayloadAction<IResponseGetAllProducts>) => {
                stopPendingAndHandleError(state, action);
            })
    })
})

export const {chooseCurrentProduct} = productSlice.actions;

export const selectProducts = (state: RootState) => state.product.products;
export const selectCurrentProduct = (state: RootState) => state.product.currentProduct;
export const selectPending = (state: RootState) => state.product.pending;


export default productSlice.reducer;
