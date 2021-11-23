import React, {useEffect} from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";

import {selectView} from "../../../../slicer/viewSlice";
import {employeeColumns} from "../../list-view/columnDefinition";
import {getEmployees, selectEmployees} from "../../../../slicer/employeeSlice";

//component imports
import GridView from "../../grid-view/GridView";
import ListView from "../../list-view/ListView";
import {parseEmployeeToThumbnail} from "../../thumbnail/helper";
import {Views} from "../../../../interfaces/IThumbnail";
//interface imports

type Props = {};

function EmployeesView(props: Props) {
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getEmployees());
    }, [dispatch]);

    const employees = useAppSelector(selectEmployees)
    const thumbnails = employees.map(parseEmployeeToThumbnail);
// const rowData = employees.map(parseEmployeeToList)

    return useAppSelector(selectView) ?
        <GridView gridItems={thumbnails} view={Views.EMPLOYEE}/> :
        <ListView rows={employees} columns={employeeColumns}/>
}

export default EmployeesView;
