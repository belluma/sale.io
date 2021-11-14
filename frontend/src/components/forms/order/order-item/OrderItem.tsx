import React from 'react'
import {useAppDispatch} from "../../../../app/hooks";
import {removeOrderItem} from "../../../../slicer/orderSlice";
//component imports
import {Grid, IconButton, Toolbar} from "@mui/material";
import ClearIcon from "@mui/icons-material/Clear"
import EditIcon from "@mui/icons-material/Edit"


//interface imports

type Props = {
    productName?: string,
    quantity?: number,
    total?: number,
    index: number
};

function OrderItem({productName, quantity, total, index}: Props) {
    const dispatch = useAppDispatch();
    const handleRemove = () => {
        dispatch(removeOrderItem(index));
    }

    return (
        <Toolbar sx={{width: 0.99}} disableGutters>
            <Grid item container xs={2}>
                <Grid item xs={6}>
                    <IconButton sx={{display: "inline"}} onClick={handleRemove}>
                        <ClearIcon/>
                    </IconButton>
                </Grid>
                <Grid item xs={6}>
                    <IconButton sx={{display: "inline"}}>
                        <EditIcon/>
                    </IconButton>
                </Grid>
            </Grid>
            <Grid item xs={6}>{productName}</Grid>
            <Grid item xs={2}>qty.: {quantity}</Grid>
            <Grid item xs={2}>€ {total?.toFixed(2)}</Grid>
        </Toolbar>
    )
}

export default OrderItem;
