import React from 'react';
import {render, screen} from '@testing-library/react';import ProtectedRoute from './ProtectedRoute';


it('renders without crashing', () => {
    render(<ProtectedRoute />);    });
