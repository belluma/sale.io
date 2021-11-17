import React from 'react'
import {selectEmployees} from "../../../slicer/employeeSlice";
import {useAppSelector} from "../../../app/hooks";

//component imports
import {Redirect} from "react-router";

//interface imports

type Props = {};

function StartRoute(props: Props){

const employees = useAppSelector(selectEmployees);
    return employees?.length ? <Redirect to={"/start"} />: <Redirect to={"/signup"}/>
}

export default StartRoute;
