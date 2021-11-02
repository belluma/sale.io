
import React from 'react';
import './App.css';


//components
import { Toolbar } from '@mui/material';
import { CssBaseline } from '@mui/material';
import Header from './components/header/Header';
import MainView from './components/main-view/MainView';


function App() {
  return (
      <React.Fragment>
      <CssBaseline/>
  <MainView />

</React.Fragment>
  );}

export default App;
