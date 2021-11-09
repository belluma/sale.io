import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';


const initialState = {
gridView: true
    }


export const viewSlice = createSlice({
    name: "view",
    initialState,
    reducers: {
        toggleView: (state) => {
            state.gridView = !state.gridView;
        }
    },
    })

export const {toggleView} = viewSlice.actions;
export const selectView = (state:RootState) => state.view.gridView;

export default viewSlice.reducer;
