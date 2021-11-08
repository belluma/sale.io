import React from 'react'
import {Grid} from "@mui/material";
import Thumbnail from "./thumbnail/Thumbnail";
import {IThumbnailData, Views} from "../../../interfaces/IThumbnailData";
import Details from "./details/Details";
import {newItemData} from "../helpers";

//component imports

//interface imports

type Props = {
    gridItems:IThumbnailData[],
    view:Views,
};

function GridView({gridItems, view}: Props){
   const thumbnailData = {title:`new ${view}`, ...newItemData};
    const thumbnails = gridItems.map(item => <Grid  item key={item.id}><Thumbnail data={item}/></Grid>)
    return(
        <Grid container spacing={2} sx={{justifyContent: {md: "left", xs: "space-around"}}}>
            {view !== Views.LOGIN && <Grid item><Thumbnail data={thumbnailData} /></Grid>}
            {thumbnails}
            <Details />
        </Grid>
    )
}

export default GridView;
