import React from 'react'
import { useAppSelector } from '../../../../app/hooks';
import { selectEmployees } from '../../../../slicer/employeeSlice';
import GridView from "../../grid-view/GridView";

//component imports

//interface imports

type Props = {};

function StartView(props: Props){
    const employees = useAppSelector(selectEmployees);
    return(
      <GridView gridItems={employees} />
    )
}

export default StartView;
