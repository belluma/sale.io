import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {IFormInput} from "../interfaces/INewItem";


const initialState = {
itemToSave:{}
    }


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

    })
export const  {handleFormInput, resetForm} = newItemSlice.actions;

export const selectItemToSave = (state: RootState) => state.newItem.itemToSave;

export default newItemSlice.reducer;
