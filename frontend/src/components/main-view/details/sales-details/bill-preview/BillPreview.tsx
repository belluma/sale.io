import React from 'react'
import {useAppSelector} from "../../../../../app/hooks";
import {selectCurrentProduct} from "../../../../../slicer/productSlice";
import {selectCurrentCustomer} from "../../../../../slicer/customerSlice";
import {getSubTotal, getTotal} from "../../../../forms/order/helper";


//component imports

import {Divider, Grid, Typography} from '@mui/material';


//interface imports

type Props = {
    quantity: number
};

function BillPreview({quantity}: Props) {
    const product = useAppSelector(selectCurrentProduct);
    const order = useAppSelector(selectCurrentCustomer);
    const subTotal = getSubTotal({product, quantity});
    const total = order.orderItems.reduce(getTotal, 0);
    return (
        <Grid container item flexDirection='row' justifyContent='flex-end'>
            <GridRow label='Price per unit:' price={`€ ${product.retailPrice?.toFixed(2)}`}/>
            <GridRow label='already on bill:' price={`€ ${total.toFixed(2)}`}/>
            <GridRow label='to add:' price={`€ ${subTotal.toFixed(2)}`}/>
            <Grid item xs={9} /><Grid item xs={3} ><Divider sx={{mr:{sm:5}, bgcolor: 'primary.main'}}/></Grid>
            <GridRow label='new total:' price={`€ ${(subTotal + total).toFixed(2)}`}/>

        </Grid>
    )
}

type GridRowProps = {
    label: string,
    price?: string
}

function GridRow({label, price}: GridRowProps) {
    return (
        <Grid item container xs={10} sm={8} justifyContent={'center'} >
            <Grid item xs={8} sm={6}>
                <Typography>{label}</Typography>
            </Grid>
            <Grid item xs={4}>
                <Typography className={'is-pulled-right pr-4'}>{price}</Typography>
            </Grid>
        </Grid>)
}

export default BillPreview;
