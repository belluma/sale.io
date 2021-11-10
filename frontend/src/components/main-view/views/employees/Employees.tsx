import React, {useEffect} from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";

import {parseEmployeeToThumbnailData, Views} from "../../../../interfaces/IThumbnailData";
import {selectView} from "../../../../slicer/viewSlice";
import {employeeColumns} from "../../list-view/columnDefinition";
import {getEmployees, selectEmployees} from "../../../../slicer/employeeSlice";

//component imports
import GridView from "../../grid-view/GridView";
import ListView from "../../list-view/ListView";
//interface imports

type Props = {};

function Employees(props: Props) {
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getEmployees());
    }, [dispatch]);

    const employees = useAppSelector(selectEmployees)
    const thumbnails = employees.map(employee => parseEmployeeToThumbnailData(employee));

    return useAppSelector(selectView) ?
        <GridView gridItems={thumbnails} view={Views.EMPLOYEE}/> :
        <ListView rows={employees} columns={employeeColumns}/>
}

export default Employees;
