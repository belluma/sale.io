import React from 'react'
import {Grid, Toolbar} from "@mui/material";
import {IProduct} from "../../../../interfaces/IProduct";

//component imports

//interface imports

type Props = {
    productName?: string,
    quantity?: number,
    total?: number,

};

function OrderItem({productName, quantity, total}: Props) {
    const formatPrice = () => {
        if(!total || !quantity) return 0.00;
        return Math.ceil(total * quantity * 100) / 100
    }
    return (
        <Toolbar>
            <Grid item xs={7}>{productName}</Grid>
            <Grid item xs={2}>qty.: {quantity}</Grid>
            <Grid item xs={3}>€ {formatPrice()}</Grid>

        </Toolbar>
    )
}

export default OrderItem;
