import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {IProductsState} from '../interfaces/IStates';
import {IResponseGetAllProducts} from "../interfaces/IApiResponse";
import {
    getAll as apiGetAll,
    getOne as apiGetOne,
    create as apiCreate,
    edit as apiEdit,
    del as apiDelete
} from '../services/apiService'
import {IProduct} from "../interfaces/IProduct";
import {handleError} from "./helper";


const initialState: IProductsState = {
    products: [],
    currentProduct: undefined,
    pending: false,
    productToSave: {},
}
const validateProduct = (state: RootState) => {
    const necessaryValues = ['name', 'suppliers', 'category', 'purchasePrice', 'unitSize']
    const setValues = Object.keys(state.product.productToSave)
    return setValues.every(v => necessaryValues.indexOf(v) >= 0);
}
const invalidProductError = {data: '', status: 406, statusText: "Not all necessary values are set"}

export const getAllProducts = createAsyncThunk<IResponseGetAllProducts, void, { state: RootState, dispatch: Dispatch }>(
    'getAll',
    async (_, {getState, dispatch}) => {
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

export const createProduct = createAsyncThunk<IResponseGetAllProducts, void, { state: RootState, dispatch: Dispatch }>(
    'create',
    async (_, {getState, dispatch}) => {
        if (!validateProduct(getState())) {
//handleError here
            return invalidProductError;
        }
        const token = getState().authentication.token
        const {data, status, statusText} = await apiCreate("product", token, getState().product.productToSave);
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
        },
        handleProductFormInput: (state, {payload}: PayloadAction<any>) => {
            state.productToSave = payload;
        },
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

export const {chooseCurrentProduct, handleProductFormInput} = productSlice.actions;

export const selectProducts = (state: RootState) => state.product.products;
export const selectCurrentProduct = (state: RootState) => state.product.currentProduct;
export const selectProductToSave = (state: RootState) => state.product.productToSave;
export const selectPending = (state: RootState) => state.product.pending;


export default productSlice.reducer;
