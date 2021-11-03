import React from 'react'
import {Grid} from "@mui/material";
import Thumbnail from "./thumbnail/Thumbnail";

//component imports

//interface imports

type Props = {
    gridItems:any[]
};

function GridView({gridItems}: Props){


    const thumbnails = gridItems.map(item => <Grid  item key={item.id}><Thumbnail item={item}/></Grid>)
    return(
        <Grid container spacing={2} sx={{justifyContent: {md: "space-between", xs: "space-around"}}}>
            {thumbnails}
        </Grid>
    )
}

export default GridView;
