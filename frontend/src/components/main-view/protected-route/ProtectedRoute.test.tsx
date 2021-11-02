import React from 'react';
import {render, screen} from '@testing-library/react';import ProtectedRoute from './ProtectedRoute';
import {store} from "../../../app/store";
import {Provider} from "react-redux";
import { BrowserRouter } from 'react-router-dom';


it('renders without crashing', () => {
    render(<BrowserRouter><Provider store={store}><ProtectedRoute path={'/test'} /></Provider></BrowserRouter>);    });
