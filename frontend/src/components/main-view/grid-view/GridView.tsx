import React from 'react'
import {Grid} from "@mui/material";
import Thumbnail from "./thumbnail/Thumbnail";
import {IThumbnailData, Views} from "../../../interfaces/IThumbnailData";
import Details from "./details/Details";

//component imports

//interface imports

type Props = {
    gridItems:IThumbnailData[]
};

function GridView({gridItems}: Props){
    const newItemData  = {
        title: `new item`,
        picture: '/pictures/add.svg',
        model: Views.LOGIN
    }
    const thumbnails = gridItems.map(item => <Grid  item key={item.id}><Thumbnail data={item}/></Grid>)
    return(
        <Grid container spacing={2} sx={{justifyContent: {md: "left", xs: "space-around"}}}>
<Thumbnail data={newItemData} />
            {thumbnails}
            <Details />
        </Grid>
    )
}

export default GridView;
