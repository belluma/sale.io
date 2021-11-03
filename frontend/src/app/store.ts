import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import authenticationReducer from '../slicer/authSlice'
import errorReducer from '../slicer/errorSlice'
import employeeReducer from '../slicer/employeeSlice';


export const store = configureStore({
  reducer: {
    authentication:authenticationReducer,
    error:errorReducer,
    employee: employeeReducer,
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
