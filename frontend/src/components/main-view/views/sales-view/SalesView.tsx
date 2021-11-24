import React from 'react'
import {Views} from "../../../../interfaces/IThumbnail";
import {parseCategoryToThumbnail} from "../../thumbnail/helper";
import GridView from "../../grid-view/GridView";

//component imports

//interface imports

type Props = {};

function SalesView(props: Props){
    const categories = ["Softdrinks", "Alkolische Getränke", "heiße Getränke", 'Salate', "Hauptgerichte", "Beilagen"]
    const thumbnails = categories.map(parseCategoryToThumbnail)
    return(
       <GridView gridItems={thumbnails} view={Views.LOGIN} />
    )
}

export default SalesView;
