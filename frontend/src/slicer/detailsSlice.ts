import { createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {IDetailsData,} from "../interfaces/IThumbnailData";
import {IDetailsState, initialDetailsData} from "../interfaces/IStates";


const initialState: IDetailsState = {
    showDetails: false,
    detailsData: initialDetailsData,
    goBack: false,
}


export const detailsSlice = createSlice({
    name: "details",
    initialState,
    reducers: {
        setDetailData: (state, action: PayloadAction<IDetailsData>) => {
            state.detailsData = action.payload
        },
        resetDetails: (state, ) => {
            state.detailsData = initialDetailsData
        },
        showDetails: (state) => {
            state.showDetails = true;
        },
        hideDetails: (state) => {
            state.showDetails = false;
            state.goBack = false;
        },
        setGoBack: (state) => {
         state.goBack = true;
        },
        resetGoBack: (state) => {
         state.goBack = false;
        },

    },

})

export const {showDetails, hideDetails, setDetailData, resetDetails, setGoBack, resetGoBack} = detailsSlice.actions;

export const selectShowDetails = (state: RootState) => state.details.showDetails;
export const selectDetailsData = (state: RootState) => state.details.detailsData;
export const selectGoBack = (state:RootState) => state.details.goBack;
export default detailsSlice.reducer;
