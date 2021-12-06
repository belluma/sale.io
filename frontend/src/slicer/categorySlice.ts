import {Action, createAsyncThunk, createSlice, Dispatch, PayloadAction, ThunkDispatch} from '@reduxjs/toolkit';
import {RootState} from '../app/store';
import {ICategoriesState} from '../interfaces/IStates';
import {IResponseGetAllCategories, IResponseGetOneCategory} from "../interfaces/IApiResponse";
import {
    getAll as apiGetAll,
    getOne as apiGetOne,
    create as apiCreate,
    edit as apiEdit,
    del as apiDelete
} from '../services/apiService'
import {hideDetails} from "./detailsSlice";
import {
    setPending,
    stopPendingAndHandleError,
    handleError,
    handleApiResponse,
} from "./errorHelper";
import {emptyCategory, ICategory} from "../interfaces/ICategory";
import history from '../services/history'

const initialState: ICategoriesState = {
    categories: [],
    current: emptyCategory,
    pending: false,
    success: false,
    toSave: emptyCategory
}
const route = "categories";

const hideDetailsAndReloadList = (dispatch: ThunkDispatch<RootState, void, Action>) => {
    dispatch(hideDetails());
    dispatch(getAllCategories());
}

export const getAllCategories = createAsyncThunk<IResponseGetAllCategories, void, { state: RootState, dispatch: Dispatch }>(
    'categories/getAll',
    async (_, {getState, dispatch}) => {
        const token = getState().authentication.token;
        const {data, status, statusText} = await apiGetAll(route, token);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const getOneCategory = createAsyncThunk<IResponseGetOneCategory, string, { state: RootState, dispatch: Dispatch }>(
    'categories/getOne',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiGetOne(route, token, id);
        handleError(status, statusText, dispatch);
        return {data, status, statusText}
    }
)

export const createCategory = createAsyncThunk<IResponseGetOneCategory, string, { state: RootState, dispatch: Dispatch }>(
    'categories/create',
    async (name, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiCreate(route, token, {name});
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch);
        return {data, status, statusText}
    }
)

export const editCategory = createAsyncThunk<IResponseGetOneCategory, ICategory, { state: RootState, dispatch: Dispatch }>(
    'categories/edit',
    async (category, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiEdit(route, token, category);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch)
        return {data, status, statusText}
    }
)

export const deleteCategory = createAsyncThunk<IResponseGetOneCategory, string, { state: RootState, dispatch: Dispatch }>(
    'categories/delete',
    async (id, {getState, dispatch}) => {
        const token = getState().authentication.token
        const {data, status, statusText} = await apiDelete(route, token, id);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch)
        return {data, status, statusText}
    }
)

export const categorySlice = createSlice({
    name: 'category',
    initialState,
    reducers: {
        chooseCurrentCategory: (state, action: PayloadAction<string>) => {
            const category = state.categories.find(cat => cat.id?.toString() === action.payload) || emptyCategory;
            state.current = category;
            history.push(`category?category=${category.name}`)
        },
        handleCategoryFormInput: (state, {payload}: PayloadAction<ICategory>) => {
            state.toSave = payload;
        },
        closeSuccess: (state: ICategoriesState) => {
            state.success = false
        }
    },
    extraReducers: (builder => {
        builder
            .addCase(getAllCategories.pending, setPending)
            .addCase(getOneCategory.pending, setPending)
            .addCase(createCategory.pending, setPending)
            .addCase(editCategory.pending, setPending)
            .addCase(deleteCategory.pending, setPending)
            .addCase(getAllCategories.fulfilled, (state, action: PayloadAction<IResponseGetAllCategories>) => {
                if (stopPendingAndHandleError(state, action, emptyCategory)) return;
                state.categories = action.payload.data;
            })
            .addCase(getOneCategory.fulfilled, (state, action: PayloadAction<IResponseGetOneCategory>) => {
                stopPendingAndHandleError(state, action, emptyCategory);
            })
            .addCase(createCategory.fulfilled, (state, action: PayloadAction<IResponseGetOneCategory>) => {
                handleApiResponse(state, action, emptyCategory)
            })
            .addCase(editCategory.fulfilled, (state, action: PayloadAction<IResponseGetOneCategory>) => {
                handleApiResponse(state, action, emptyCategory)
            })
            .addCase(deleteCategory.fulfilled, (state, action: PayloadAction<IResponseGetOneCategory>) => {
                handleApiResponse(state, action, emptyCategory)
            })
    })
})

export const {chooseCurrentCategory, handleCategoryFormInput, closeSuccess} = categorySlice.actions;

export const selectCategories = (state: RootState) => state.category.categories;
export const selectCurrentCategory = (state: RootState) => state.category.current;
export const selectCategoryToSave = (state: RootState) => state.category.toSave;
export const selectCategoryPending = (state: RootState) => state.category.pending;
export const selectCategorySuccess = (state: RootState) => state.category.success;


export default categorySlice.reducer;
