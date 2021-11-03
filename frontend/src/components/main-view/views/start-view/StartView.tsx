import React from 'react'
import { useAppSelector } from '../../../../app/hooks';
import { selectLoggedIn } from '../../../../slicer/authSlice';

//component imports
import {Redirect} from "react-router";

//interface imports

type Props = {};

function StartView(props: Props){
    const loggedIn = useAppSelector(selectLoggedIn);
    return(
       !loggedIn ? <Redirect to={'/login'} /> : <div>StartView</div>
       // <div>StartView</div>
    )
}

export default StartView;
