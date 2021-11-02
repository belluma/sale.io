import React from 'react';
import {render, screen} from '@testing-library/react';
import Signup from './Signup';
import { Provider } from 'react-redux';
import { store } from '../../../app/store';


it('renders without crashing', () => {
    render(<Provider store={store}><Signup /></Provider>);    });
