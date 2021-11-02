import React from 'react';
import {render, screen} from '@testing-library/react';
import MainView from './MainView';
import { BrowserRouter } from 'react-router-dom';


it('renders without crashing', () => {
    render( <BrowserRouter><MainView /></BrowserRouter>);    });
