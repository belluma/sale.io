import React from 'react';
import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from './app/store';
import App from './App';
import {BrowserRouter as Router} from 'react-router-dom'

test('renders component', () => {
    render(
        <Provider store={store}>
            <Router>
                <App />
            </Router>
        </Provider>
    );

});
