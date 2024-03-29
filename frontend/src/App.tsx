import React from 'react';
import './App.css';
import {useAppDispatch} from "./app/hooks";
import {getEmployees} from "./slicer/employeeSlice";
import {loginFromStorage} from "./slicer/authSlice";
import colorTheme from './theme'

//components
import {CssBaseline, ThemeProvider, Toolbar, useMediaQuery, useTheme} from '@mui/material';
import Header from './components/header/Header';
import MainView from './components/main-view/MainView';
import {gridView} from "./slicer/viewSlice";


function App() {
    const dispatch = useAppDispatch();
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
    if(isMobile){
        dispatch(gridView())
    }
    dispatch(getEmployees());
    dispatch(loginFromStorage());
    return (
        <ThemeProvider theme={colorTheme}>
        <React.Fragment>
            <CssBaseline/>
            <Header/>
            <Toolbar/>
            <MainView/>

        </React.Fragment>
        </ThemeProvider>
    );
}

export default App;
