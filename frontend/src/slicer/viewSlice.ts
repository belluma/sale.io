import {createSlice} from '@reduxjs/toolkit';
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
        },
        gridView: (state) => {
            state.gridView = true;
        }
    },
    })

export const {toggleView, gridView} = viewSlice.actions;
export const selectView = (state:RootState) => state.view.gridView;

export default viewSlice.reducer;
