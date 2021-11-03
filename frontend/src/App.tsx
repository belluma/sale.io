
import React from 'react';
import './App.css';
import {useAppDispatch} from "./app/hooks";
import {getEmployees} from "./slicer/employeeSlice";

//components
import { CssBaseline, Toolbar } from '@mui/material';
import Header from './components/header/Header';
import MainView from './components/main-view/MainView';
import {loginFromStorage} from "./slicer/authSlice";



function App() {
  const dispatch = useAppDispatch();
  dispatch(getEmployees());
  dispatch(loginFromStorage());
  return (
      <React.Fragment>
      <CssBaseline/>
  <Header />
  <Toolbar/>
  <MainView />

</React.Fragment>
  );}

export default App;
