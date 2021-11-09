import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import authenticationReducer from '../slicer/authSlice'
import errorReducer from '../slicer/errorSlice'
import employeeReducer from '../slicer/employeeSlice';
import detailsReducer from '../slicer/detailsSlice';
import productReducer from '../slicer/productSlice';
import newItemReducer from '../slicer/newItemSlice';
import supplierReducer from '../slicer/supplierSlice';


export const store = configureStore({
  reducer: {
    authentication:authenticationReducer,
    error:errorReducer,
    employee: employeeReducer,
    details: detailsReducer,
    product: productReducer,
    supplier: supplierReducer,
  },
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
