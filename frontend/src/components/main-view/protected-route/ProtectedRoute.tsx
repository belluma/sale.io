import React from 'react'
import {useAppSelector} from "../../../app/hooks";
import {selectLoggedIn} from "../../../slicer/authSlice";
import {selectEmployees} from "../../../slicer/employeeSlice";

//component imports
import {Redirect, Route} from "react-router";

//interface imports

type Props = {
    path:string,
    component?:any
};
function ProtectedRoute(props: Props){
    const loggedIn = useAppSelector(selectLoggedIn);
    const employees = useAppSelector(selectEmployees);
    const redirect = employees?.length ? "/login" : "/signup";
console.log(loggedIn)
    return(
        loggedIn ? <Route {...props} /> : <Redirect to={redirect}/>
    )
}

export default ProtectedRoute;
