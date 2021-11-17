import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import authenticationReducer from '../slicer/authSlice'
import employeeReducer from '../slicer/employeeSlice';
import detailsReducer from '../slicer/detailsSlice';
import productReducer from '../slicer/productSlice';
import supplierReducer from '../slicer/supplierSlice';
import viewReducer from '../slicer/viewSlice';
import orderReducer from '../slicer/orderSlice';


export const store = configureStore({
  reducer: {
    authentication:authenticationReducer,
    employee: employeeReducer,
    details: detailsReducer,
    product: productReducer,
    supplier: supplierReducer,
    view: viewReducer,
    order: orderReducer,
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
