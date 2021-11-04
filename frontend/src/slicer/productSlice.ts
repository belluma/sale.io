import {createAsyncThunk, createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import { IProductsState } from '../interfaces/IStates';
import {getAllProducts} from '../services/productService';
import { getEmployees } from './employeeSlice';
import {getErrorMessage} from "./errorSlice";
import {IResponseGetAllProducts} from "../interfaces/IApiResponse";


const initialState:IProductsState = {
    products:[],
    currentProduct: undefined,
    pending: false,
}


export const getProducts = createAsyncThunk(
    'getProducts',
    async (_, {getState, dispatch}) => {
        //@ts-ignore
        const token = getState().login.token;
        const {data, status, statusText} = await getAllProducts(token);
        if (status !== 200) {
            dispatch(getErrorMessage({status, statusText}))
        }
        return {data, status, statusText}
    }
)



export const productSlice = createSlice({
    name: 'product',
    initialState,
    reducers: {
        chooseCurrentProduct: (state, action:PayloadAction<number>) => {
            state.currentProduct = state.products.filter(p => p.id === action.payload)[0];
        }
    },
    extraReducers: (builder => {
        builder
            .addCase(getEmployees.pending, state => {
                state.pending = true;
            })
            .addCase(getEmployees.fulfilled, (state, action: PayloadAction<IResponseGetAllProducts>) => {
                if (action.payload.status !== 200) {
                    return;
                }
                state.pending = false;
                state.products = action.payload.data;
            })
    })
})

export const {chooseCurrentProduct} = productSlice.actions;

export const selectProducts = (state: RootState) => state.product.products;
export const selectCurrentProduct = (state: RootState) => state.product.currentProduct;
export const selectPending = (state: RootState) => state.product.pending;


export default productSlice.reducer;
