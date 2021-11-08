import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {IAPIState} from "../interfaces/IStates";
import {ICustomer} from "../interfaces/ICustomer";
import {IEmployee} from "../interfaces/IEmployee";
import {IProduct} from "../interfaces/IProduct";
import {ISupplier} from "../interfaces/ISupplier";
import {getErrorMessage} from "./errorSlice";
import {IApiPayload} from "../interfaces/IApiResponse";


const initialState: IAPIState = {
    customers: [],
    employees: [],
    products: [],
    suppliers: [],
    pending: false,
    selectedEntity: undefined,
}

type Model = "employees"

export const getAll = createAsyncThunk<IApiPayload, Model, { state: RootState, dispatch: Dispatch }>(
    'getAll',
    async (model, {getState, dispatch}) => {
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiGetAll(model, token);
        if (status !== 200) {
            dispatch(getErrorMessage({status, statusText}))
        }
        return {data, status, statusText, model}
    }
)
//
// export const getOne = createAsyncThunk<IApiPayload, string, { state: RootState, dispatch: Dispatch }>(
//     'getOne',
//     async (model, {getState, dispatch}) => {
//         const token = getState().authentication.token
//         const {data, status, statusText} = await apiGetOne(model, token);
//         if (status !== 200) {
//             dispatch(getErrorMessage({status, statusText}))
//         }
//         return {data, status, statusText, model}
//     }
// )
//
// export const create = createAsyncThunk<IApiPayload, string, { state: RootState, dispatch: Dispatch }>(
//     'create',
//     async (model, {getState, dispatch}) => {
//         const token = getState().authentication.token
//         const {data, status, statusText} = await apiCreate(model, token);
//         if (status !== 200) {
//             dispatch(getErrorMessage({status, statusText}))
//         }
//         return {data, status, statusText, model}
//     }
// )
//
// export const edit = createAsyncThunk<IApiPayload, string, { state: RootState, dispatch: Dispatch }>(
//     'edit',
//     async (model, {getState, dispatch}) => {
//         const token = getState().authentication.token
//         const {data, status, statusText} = await apiEdit(model, token);
//         if (status !== 200) {
//             dispatch(getErrorMessage({status, statusText}))
//         }
//         return {data, status, statusText, model}
//     }
// )
//
// export const deleteItem = createAsyncThunk<IApiPayload, string, { state: RootState, dispatch: Dispatch }>(
//     'delete',
//     async (model, {getState, dispatch}) => {
//         const token = getState().authentication.token
//         const {data, status, statusText} = await apiDelete(model, token);
//         if (status !== 200) {
//             dispatch(getErrorMessage({status, statusText}))
//         }
//         return {data, status, statusText, model}
//     }
// )


export const apiSlice = createSlice({
    name: "api",
    initialState,
    reducers: {},
    extraReducers: builder => {
        const setPending = (state: IAPIState, action: PayloadAction<IApiPayload>) => {
            state.pending = true
        }
        builder
            .addCase(getAll.pending, setPending)
            // .addCase(getOne.pending, setPending)
            // .addCase(create.pending, setPending)
            // .addCase(edit.pending, setPending)
            // .addCase(deleteItem.pending, setPending)
            .addCase(getAll.fulfilled, (state: IAPIState, action: PayloadAction<IApiPayload>) => {
                state.pending = false;
                  if (action.payload.status !== 200) {
                return;
            }
                  state["employees"] = action.payload.data;
            state[action.payload.model] = action.payload.data;
            })
            // .addCase(getOne.fulfilled, (state: IAPIState, {payload}: PayloadAction<IApiPayload>) => {
            //     state.pending = false;
            //       if (payload.status !== 200) {
            //     return;
            // }
            // state[payload.model] = payload.data;
            //
            // })
            // .addCase(create.fulfilled, (state: IAPIState, {payload}: PayloadAction<IApiPayload>) => {
            //     state.pending = false;
            //       if (payload.status !== 200) {
            //     return;
            // }
            // state[payload.model] = payload.data;
            //
            // })
            // .addCase(edit.fulfilled, (state: IAPIState, {payload}: PayloadAction<IApiPayload>) => {
            //     state.pending = false;
            //       if (payload.status !== 200) {
            //     return;
            // }
            // state[payload.model] = payload.data;
            //
            // })
            // .addCase(deleteItem.fulfilled, (state: IAPIState, {payload}: PayloadAction<IApiPayload>) => {
            //     state.pending = false;
            //       if (payload.status !== 200) {
            //     return;
            // }
            // state[payload.model] = payload.data;
            //
            // })
    }
})


export default apiSlice.reducer;
