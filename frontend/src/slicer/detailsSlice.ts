import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';


const initialState = {
showDetails: false,
    }


export const detailsSlice = createSlice({
    name:"details" ,
    initialState,
    reducers: {
        showDetails: (state) => {
            state.showDetails = true;
        },
        hideDetails: (state) => {
            state.showDetails = false;
        }
    },

    })

export const {showDetails, hideDetails} = detailsSlice.actions;

export const selectShowDetails = (state: RootState) => state.details.showDetails;
export default detailsSlice.reducer;
