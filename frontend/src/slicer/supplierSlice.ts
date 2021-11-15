import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {ISuppliersState} from '../interfaces/IStates';
import {IResponseGetAllSuppliers, IResponseGetOneSupplier} from "../interfaces/IApiResponse";
import {
    getAll as apiGetAll,
    getOne as apiGetOne,
    create as apiCreate,
    edit as apiEdit,
    del as apiDelete
} from '../services/apiService'
import {emptySupplier, ISupplier} from "../interfaces/ISupplier";
import {handleError, invalidDataError} from './errorHelper';
import {setPending, stopPendingAndHandleError} from "./errorHelper";


const initialState: ISuppliersState = {
    suppliers: [],
    current: undefined,
    toSave: emptySupplier,
    pending: false,
}
const route = "suppliers";
export const validateSupplier = (supplier: ISupplier): boolean => {
    const name = !!supplier.firstName?.length || !!supplier.lastName?.length;
    const contact = !!supplier.email?.length || !!supplier.phone?.length;
    return name && contact;
}
const validateBeforeSendingToBackend = ({supplier}: RootState): boolean => {
    return validateSupplier(supplier.toSave);
}

export const getAllSuppliers = createAsyncThunk<IResponseGetAllSuppliers, void, { state: RootState, dispatch: Dispatch }>(
    'suppliers/getAll',
    async (_, {getState, dispatch}) => {
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiGetAll(route, token);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const getOneSupplier = createAsyncThunk<IResponseGetOneSupplier, string, { state: RootState, dispatch: Dispatch }>(
    'suppliers/getOne',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiGetOne(route, token, id);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const createSupplier = createAsyncThunk<IResponseGetOneSupplier, void, { state: RootState, dispatch: Dispatch }>(
    'suppliers/create',
    async (_, {getState, dispatch}) => {
        if (!validateBeforeSendingToBackend(getState())) {
            return invalidDataError;
        }
        const token = getState().authentication.token
        const {data, status, statusText} = await apiCreate(route, token, getState().supplier.toSave);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const editSupplier = createAsyncThunk<IResponseGetOneSupplier, ISupplier, { state: RootState, dispatch: Dispatch }>(
    'suppliers/edit',
    async (supplier, {getState, dispatch}) => {
        if (!validateBeforeSendingToBackend(getState())) {
            return invalidDataError;
        }
        const token = getState().authentication.token
        const {data, status, statusText} = await apiEdit(route, token, supplier);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const deleteSupplier = createAsyncThunk<IResponseGetOneSupplier, string, { state: RootState, dispatch: Dispatch }>(
    'suppliers/delete',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiDelete(route, token, id);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const supplierSlice = createSlice({
    name: 'supplier/suppliers',
    initialState,
    reducers: {
        chooseCurrentSupplier: (state: ISuppliersState, action: PayloadAction<string>) => {
            state.current = state.suppliers.find(supplier => supplier.id === action.payload);
        },
        handleSupplierFormInput: (state, {payload}: PayloadAction<ISupplier>) => {
            state.toSave = payload;
        },
    },
    extraReducers: (builder => {
        builder
            .addCase(getAllSuppliers.pending, setPending)
            .addCase(getOneSupplier.pending, setPending)
            .addCase(createSupplier.pending, setPending)
            .addCase(editSupplier.pending, setPending)
            .addCase(deleteSupplier.pending, setPending)
            .addCase(getAllSuppliers.fulfilled, (state: ISuppliersState, action: PayloadAction<IResponseGetAllSuppliers>) => {
                if (stopPendingAndHandleError(state, action, emptySupplier)) return;
                state.suppliers = action.payload.data;
            })
            .addCase(getOneSupplier.fulfilled, (state: ISuppliersState, action: PayloadAction<IResponseGetOneSupplier>) => {
                stopPendingAndHandleError(state, action, emptySupplier);
            })
            .addCase(createSupplier.fulfilled, (state: ISuppliersState, action: PayloadAction<IResponseGetOneSupplier>) => {
                stopPendingAndHandleError(state, action, emptySupplier);
            })
            .addCase(editSupplier.fulfilled, (state: ISuppliersState, action: PayloadAction<IResponseGetOneSupplier>) => {
                stopPendingAndHandleError(state, action, emptySupplier);
            })
            .addCase(deleteSupplier.fulfilled, (state: ISuppliersState, action: PayloadAction<IResponseGetOneSupplier>) => {
                stopPendingAndHandleError(state, action, emptySupplier);
            })
    })
})

export const {chooseCurrentSupplier, handleSupplierFormInput} = supplierSlice.actions;

export const selectSuppliers = (state: RootState) => state.supplier.suppliers;
export const selectCurrentSupplier = (state: RootState) => state.supplier.current;
export const selectSupplierToSave = (state: RootState) => state.supplier.toSave;
export const selectSupplierPending = (state: RootState) => state.supplier.pending;


export default supplierSlice.reducer;
