import {createAsyncThunk, createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {IDetailsData} from "../interfaces/IThumbnailData";
import {IDetailsState, initialDetailsData} from "../interfaces/IStates";
import {resetForm} from "./newItemSlice";


const initialState: IDetailsState = {
    showDetails: false,
    detailsData: initialDetailsData,
}

export const hideDetails = createAsyncThunk(
    'hideDetails',
    (_, thunkAPI) => {
        thunkAPI.dispatch(resetForm())
    })


export const detailsSlice = createSlice({
    name: "details",
    initialState,
    reducers: {
        setDetailData: (state, action: PayloadAction<IDetailsData>) => {
            state.detailsData = action.payload
        },
        resetDetails: (state) => {
            state.detailsData = initialDetailsData
        },
        showDetails: (state) => {
            state.showDetails = true;
        },
    },
    extraReducers: (builder => {
        builder.addCase(hideDetails.fulfilled, (state) => {
            state.showDetails = false;
        })
    })

})

export const {showDetails, setDetailData, resetDetails} = detailsSlice.actions;

export const selectShowDetails = (state: RootState) => state.details.showDetails;
export const selectDetailsData = (state: RootState) => state.details.detailsData;
export default detailsSlice.reducer;
