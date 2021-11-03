import React from 'react'
import {selectEmployees, selectPending} from "../../../slicer/employeeSlice";
import {useAppSelector} from "../../../app/hooks";
import StartView from '../views/start-view/StartView';
import {Redirect, Route} from "react-router";

//component imports

//interface imports

type Props = {};

function StartRoute(props: Props){

const pending = useAppSelector(selectPending);
const employees = useAppSelector(selectEmployees);
const route = employees?.length ? <Redirect to={"/start"} />: <Redirect to={"/signup"}/>
    return pending ? (<div>spinner</div>) : route
}

export default StartRoute;
