import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {IDetailsData} from "../interfaces/IThumbnailData";
import {IDetailsState, initialDetailsData} from "../interfaces/IStates";


const initialState: IDetailsState = {
    showDetails: false,
    detailsData: initialDetailsData,
}


export const detailsSlice = createSlice({
    name: "details",
    initialState,
    reducers: {
        setDetailData: (state, action: PayloadAction<IDetailsData>) => {
            state.detailsData = action.payload
        },
        showDetails: (state) => {
            state.showDetails = true;
        },
        hideDetails: (state) => {
            state.showDetails = false;
        }
    },

})

export const {showDetails, hideDetails, setDetailData} = detailsSlice.actions;

export const selectShowDetails = (state: RootState) => state.details.showDetails;
export const selectDetailsData = (state: RootState) => state.details.detailsData;
export default detailsSlice.reducer;
