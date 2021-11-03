import React from 'react';
import {render, screen} from '@testing-library/react';
import StartView from './StartView';


it('renders without crashing', () => {
    render(<StartView />);    });
