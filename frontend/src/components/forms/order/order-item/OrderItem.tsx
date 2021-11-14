import React from 'react'

//component imports
import {Grid, IconButton, Toolbar} from "@mui/material";
import ClearIcon from "@mui/icons-material/Clear"

//interface imports

type Props = {
    productName?: string,
    quantity?: number,
    total?: number,

};

function OrderItem({productName, quantity, total}: Props) {
    const formatPrice = () => {
        if(!total || !quantity) return 0.00;
        return (Math.ceil(total * quantity * 100) / 100).toFixed(2);
    }
    return (
        <Toolbar>
            <Grid item xs={1}>
            <IconButton>
                <ClearIcon/>.
            </IconButton>
            </Grid>
            <Grid item xs={6}>{productName}</Grid>
            <Grid item xs={2}>qty.: {quantity}</Grid>
            <Grid item xs={3}>€ {formatPrice()}</Grid>
        </Toolbar>
    )
}

export default OrderItem;
