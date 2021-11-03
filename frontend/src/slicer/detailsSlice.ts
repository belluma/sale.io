import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {IDetailsData} from "../interfaces/IThumbnailData";
import {IDetailsState} from "../interfaces/IStates";


const initialState: IDetailsState = {
showDetails: false,
    selectedDetails: undefined,
    }


export const detailsSlice = createSlice({
    name:"details" ,
    initialState,
    reducers: {
        selectDetails: (state, action:PayloadAction<IDetailsData>) => {
            state.selectedDetails = action.payload
        },
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
