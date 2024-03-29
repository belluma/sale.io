import React from 'react'
import {useAppSelector} from '../../../../app/hooks';
import {selectEmployees} from '../../../../slicer/employeeSlice';
import {
    Views
} from "../../../../interfaces/IThumbnail";

//component imports
import GridView from "../../grid-view/GridView";
import {selectLoggedIn} from "../../../../slicer/authSlice";
import {Redirect} from "react-router";
import {parseEmployeeToLoginThumbnail} from "../../thumbnail/helper";

//interface imports

type Props = {};

function LoginView(props: Props){
    const loggedIn = useAppSelector(selectLoggedIn);
    const employees = useAppSelector(selectEmployees).map(parseEmployeeToLoginThumbnail);
    return(
     loggedIn ? <Redirect to={'/start'} /> :  <GridView gridItems={employees} view={Views.LOGIN}/>
    )
}

export default LoginView;
