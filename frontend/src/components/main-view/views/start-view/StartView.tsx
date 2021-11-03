import React from 'react'
import { useAppSelector } from '../../../../app/hooks';
import { selectEmployees } from '../../../../slicer/employeeSlice';
import GridView from "../../grid-view/GridView";
import {parseEmployeeToThumbnailData} from "../../../../interfaces/IThumbnailData";

//component imports

//interface imports

type Props = {};

function StartView(props: Props){
    const employees = useAppSelector(selectEmployees).map(employee => parseEmployeeToThumbnailData(employee));
    return(
      <GridView gridItems={employees} />
    )
}

export default StartView;
