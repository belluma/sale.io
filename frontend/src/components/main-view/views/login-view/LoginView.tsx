import React from 'react'
import {useAppSelector} from '../../../../app/hooks';
import {selectEmployees} from '../../../../slicer/employeeSlice';
import {
    parseEmployeeToLoginThumbnailData,
    Views
} from "../../../../interfaces/IThumbnailData";

//component imports
import GridView from "../../grid-view/GridView";
import {selectLoggedIn} from "../../../../slicer/authSlice";
import {Redirect} from "react-router";

//interface imports

type Props = {};

function LoginView(props: Props){
    const loggedIn = useAppSelector(selectLoggedIn);
    const employees = useAppSelector(selectEmployees).map(employee => parseEmployeeToLoginThumbnailData(employee));
    return(
     loggedIn ? <Redirect to={'/start'} /> :  <GridView gridItems={employees} view={Views.LOGIN}/>
    )
}

export default LoginView;
