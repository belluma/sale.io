import React from 'react'
import {useAppSelector} from "../../../../app/hooks";
import {selectCurrentProduct} from "../../../../slicer/productSlice";
import { Divider, Grid, Typography} from "@mui/material";
import {parseName} from "../../../../interfaces/IThumbnailData";

//component imports

//interface imports

type Props = {};

function ProductDetails(props: Props) {
    const product = useAppSelector(selectCurrentProduct);
    const {
        suppliers,
        purchasePrice,
        retailPrice,
        minAmount,
        maxAmount,
        unitSize,
        amountInStock
    } = product;

    return (
        <Grid container justifyContent="space-between" alignItems="center" direction="column" sx={{height:.8}}>
            <Grid item>
            <Typography variant="h5">current
                supplier: {suppliers?.length ? parseName(suppliers[0]) : "no supplier found"}
            </Typography>
            <Divider/>
            </Grid>
            <Grid container item columnSpacing={6} rowSpacing={1}>
                <Grid item xs={6}>
                    <Typography variant="body1">purchase
                        price: {purchasePrice ? '€' : ''}{purchasePrice?.toFixed(2)}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="body1">retail
                        price: €{retailPrice?.toFixed(2) || "0.00"}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="body1">min. Amount: {minAmount}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="body1">max. Amount: {maxAmount}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="body1">unit Size: {unitSize}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="body1">amount in stock: {amountInStock}</Typography>
                </Grid>
            </Grid>
        </Grid>
    )
}

export default ProductDetails;
