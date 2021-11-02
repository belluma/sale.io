import React from 'react';
import {render, screen} from '@testing-library/react';
import ErrorMessage from './ErrorMessage';


it('renders without crashing', () => {
    render(<ErrorMessage />);    });
