import {createAsyncThunk, createSlice, Dispatch, PayloadAction} from '@reduxjs/toolkit';
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
    invalidDataError,
    handleApiResponse,
} from "./errorHelper";
import {emptyCategory, ICategory} from "../interfaces/ICategory";


const initialState: ICategoriesState = {
    categories: [],
    current: emptyCategory,
    pending: false,
    success: false,
    toSave: emptyCategory
}
const route = "categories";

export const validateCategory = (category: ICategory): boolean => {
    const necessaryValues = ['name', 'suppliers', 'purchasePrice', 'unitSize']
    //@ts-ignore values defined in line above must be keys of ICategory
    if (necessaryValues.every(v => !!category[v])) {
        //@ts-ignore line above checks that values are not undefined
        return category.name.length > 0 && category.suppliers.length > 0 && category.purchasePrice > 0 && category.unitSize > 0
    }
    return false
}

const validateBeforeSendingToBackend = ({category}: RootState) => {
    return validateCategory(category.toSave);
}

const hideDetailsAndReloadList = (dispatch: Dispatch) => {
    dispatch(hideDetails());
    //@ts-ignore
    dispatch(getAllOpenCategories());
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

export const createCategory = createAsyncThunk<IResponseGetOneCategory, void, { state: RootState, dispatch: Dispatch }>(
    'categories/create',
    async (_, {getState, dispatch}) => {
        console.log(123)
        const token = getState().authentication.token
        const {data, status, statusText} = await apiCreate(route, token, getState().category.toSave);
        handleError(status, statusText, dispatch);
        if (status === 200) hideDetailsAndReloadList(dispatch);
        return {data, status, statusText}
    }
)

export const editCategory = createAsyncThunk<IResponseGetOneCategory, ICategory, { state: RootState, dispatch: Dispatch }>(
    'categories/edit',
    async (category, {getState, dispatch}) => {
        if (!validateBeforeSendingToBackend(getState())) {
            return invalidDataError;
        }
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
            state.current = state.categories.find(category => category.id?.toString() === action.payload) || emptyCategory;
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
