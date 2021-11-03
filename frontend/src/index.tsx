import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { store } from './app/store';
import { Provider } from 'react-redux';
import * as serviceWorker from './serviceWorker';
import history from './services/history';
import {Router } from "react-router-dom";


ReactDOM.render(
  <React.StrictMode>
      <Router history={history}>
    <Provider store={store}>
      <App />
    </Provider>
      </Router>
  </React.StrictMode>,
  document.getElementById('root')
);


serviceWorker.unregister();
