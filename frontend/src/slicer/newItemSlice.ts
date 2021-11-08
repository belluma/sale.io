import {createAsyncThunk, createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {INewItemState} from "../interfaces/IStates";
import {create} from "../services/apiService";
import {getErrorMessage} from "./errorSlice";


const initialState: INewItemState = {
itemToSave:{},
    pending: false
    }

    export const saveItem = createAsyncThunk(
        'saveItem',
        async(item:string, {getState, dispatch}) => {
            console.log(item)
            //@ts-ignore
            const token = getState().login.token;
            //@ts-ignore
            const itemToSave = getState().newItem.itemToSave;
            const {data, status, statusText} = await create(item, token, itemToSave);
            if (status !== 200) {
                dispatch(getErrorMessage({status, statusText}))
            }
            return {data, status, statusText}
        }
    )


export const newItemSlice = createSlice({
    name: 'newItem' ,
    initialState,
    reducers: {
        handleFormInput: (state, {payload}:PayloadAction<any>) => {
            state.itemToSave = payload;
        },
        resetForm: (state) => {
            state.itemToSave = {};
        }
    },
    extraReducers:(builder => {
        builder
            .addCase(saveItem.pending, state => {
                state.pending = true
            })
            .addCase(saveItem.fulfilled, (state, {payload}: PayloadAction<any>) => {
                if (payload.status !== 200) {
                    return;
                }
                state.savedItem = payload.data;
                state.pending = false;
                state.itemToSave = {};
            })
    })

    })
export const  {handleFormInput, resetForm} = newItemSlice.actions;

export const selectItemToSave = (state: RootState) => state.newItem.itemToSave;

export default newItemSlice.reducer;
