
import React from 'react';
import './App.css';
import {useAppDispatch} from "./app/hooks";
import {getEmployees} from "./slicer/employeeSlice";

//components
import { Toolbar } from '@mui/material';
import { CssBaseline } from '@mui/material';
import Header from './components/header/Header';
import MainView from './components/main-view/MainView';



function App() {
  const dispatch = useAppDispatch();
  dispatch(getEmployees);
  return (
      <React.Fragment>
      <CssBaseline/>
  <Header />
  <Toolbar/>
  <MainView />

</React.Fragment>
  );}

export default App;
