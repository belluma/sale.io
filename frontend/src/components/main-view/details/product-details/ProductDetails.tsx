import React from 'react'
import {useAppSelector} from "../../../../app/hooks";
import {selectCurrentProduct} from "../../../../slicer/productSlice";
import {Container, Divider, Grid, Typography} from "@mui/material";
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
    //@ts-ignore
    console.log(suppliers)
    return (
        <>
            <Typography variant="h5">current
                supplier: {suppliers?.length ? parseName(suppliers[0]) : "no supplier found"}</Typography>
            <Divider/>
            <Grid container columnSpacing={12}>
                <Grid item xs={6}>
                    <Typography variant="h6">purchase
                        price: {purchasePrice ? '€' : ''}{purchasePrice?.toFixed(2)}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="h6">retail
                        price: {retailPrice ? '€' : ''}{retailPrice?.toFixed(2)}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="h6">min. Amount: {minAmount}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="h6">max. Amount: {maxAmount}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="h6">unit Size: {unitSize}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="h6">amount in stock: {amountInStock}</Typography>
                </Grid>
            </Grid>
        </>
    )
}

export default ProductDetails;
