import React from 'react'
import {Views} from "../../../../interfaces/IThumbnail";
import {parseCategoryToThumbnail} from "../../thumbnail/helper";
import GridView from "../../grid-view/GridView";
import {useAppSelector} from "../../../../app/hooks";
import {selectCurrentCustomer} from "../../../../slicer/customerSlice";
import {Redirect} from "react-router";

//component imports

//interface imports

type Props = {};

function SalesView(props: Props){
    const currentCustomer = useAppSelector(selectCurrentCustomer)
    const categories = ["Softdrinks", "Alkolische Getränke", "heiße Getränke", 'Salate', "Hauptgerichte", "Beilagen"]
    const thumbnails = categories.map(parseCategoryToThumbnail)
    return(
        !currentCustomer.id ? <Redirect to={'start'}  /> : <GridView gridItems={thumbnails} view={Views.LOGIN} />
    )
}

export default SalesView;
