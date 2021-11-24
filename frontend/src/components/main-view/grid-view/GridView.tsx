import React from 'react'
import {newItemThumbnail} from "../helpers";
import {useAppSelector} from "../../../app/hooks";
import {selectView} from "../../../slicer/viewSlice";
import {useHistory, useLocation} from "react-router";

import 'bulma/css/bulma.css';
//component imports

import Details from "../details/Details";
import {Fab, Grid} from "@mui/material";
import ArrowBackIcon from '@mui/icons-material/ArrowBack'
import Thumbnail from "../thumbnail/Thumbnail";

//interface imports
import {IThumbnail, Views} from "../../../interfaces/IThumbnail";
import {selectCurrentCustomer} from "../../../slicer/customerSlice";
import {parseCustomerToThumbnail} from "../thumbnail/helper";

type Props = {
    gridItems: IThumbnail[],
    view: Views,
    customer?: boolean
};

function GridView({gridItems, view, customer}: Props) {
    const path = useLocation().pathname;
    const history = useHistory();

    const isCategory = path.indexOf('category') >= 0 || path.indexOf('sales') >= 0;
    const isHidden = useAppSelector(selectView) ? "" : "is-hidden";
    const thumbnailProps = customer ? {...newItemThumbnail, model: Views.NEWCUSTOMER, id: '0'} : newItemThumbnail
    const thumbnails = gridItems.map(item => <Grid item key={item.id}><Thumbnail data={item}/></Grid>)
    const currentOrder = useAppSelector(selectCurrentCustomer);
    const orderPreview = <Grid item><Thumbnail data={parseCustomerToThumbnail(currentOrder)}/></Grid>
    const newItemCard = <Grid item><Thumbnail data={{title: `new ${view}`, ...thumbnailProps}}/></Grid>;
    const buttonProps = {position: 'absolute', top: -30, right: 25} as const;
    const goBackButton =
        <Fab onClick={history.goBack} color={'primary'} sx={buttonProps}>
            <ArrowBackIcon/>
        </Fab>

    return (
        <Grid className={isHidden} container spacing={2}
              sx={{position: 'relative', justifyContent: {md: "left", xs: "space-around"}}}>
            {isCategory && goBackButton}
            {isCategory && orderPreview}
            {view !== Views.LOGIN && newItemCard}
            {thumbnails}
            <Details/>
        </Grid>
    )
}


export default GridView;
