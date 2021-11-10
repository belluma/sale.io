import React from 'react';
import './App.css';
import {useAppDispatch} from "./app/hooks";
import {getEmployees} from "./slicer/employeeSlice";
import {loginFromStorage} from "./slicer/authSlice";

//components
import {CssBaseline, Toolbar} from '@mui/material';
import Header from './components/header/Header';
import MainView from './components/main-view/MainView';


function App() {
    const dispatch = useAppDispatch();
    dispatch(getEmployees());
    dispatch(loginFromStorage());
    return (
        <React.Fragment>
            <CssBaseline/>
            <Header/>
            <Toolbar/>
            <MainView/>

        </React.Fragment>
    );
}

export default App;
