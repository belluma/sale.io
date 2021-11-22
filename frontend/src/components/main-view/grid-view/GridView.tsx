import React from 'react'
import {newItemData} from "../helpers";
import 'bulma/css/bulma.css';

//component imports
import Details from "../details/Details";
import {Grid} from "@mui/material";
import Thumbnail from "../thumbnail/Thumbnail";
//interface imports

import {IThumbnail, Views} from "../../../interfaces/IThumbnail";
import {useAppSelector} from "../../../app/hooks";
import {selectView} from "../../../slicer/viewSlice";

type Props = {
    gridItems:IThumbnail[],
    view:Views,
};

function GridView({gridItems, view}: Props){
    const isHidden = useAppSelector(selectView) ? "" : "is-hidden";
   const thumbnailData = {title:`new ${view}`, ...newItemData};
    const thumbnails = gridItems.map(item => <Grid  item key={item.id}><Thumbnail data={item}/></Grid>)
    return(
        <Grid className={isHidden} container spacing={2} sx={{justifyContent: {md: "left", xs: "space-around"}}}>
            {view !== Views.LOGIN && <Grid item><Thumbnail data={thumbnailData} /></Grid>}
            {thumbnails}
            <Details />
        </Grid>
    )
}

export default GridView;
