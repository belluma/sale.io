import React from 'react'
import {selectOrderToSave} from "../../../../slicer/orderSlice";
import { getTotal} from "../helper";

import {useAppSelector} from "../../../../app/hooks";
//component imports
import OrderItem from "../order-item/OrderItem";
import {Card, CardActions, CardHeader, Divider, Toolbar} from "@mui/material";
import CardContent from '@material-ui/core/CardContent';

import Grid from '@material-ui/core/Grid';
//interface imports

type Props = {};

function OrderPreview(props: Props) {
    const order =  useAppSelector(selectOrderToSave);
    const {orderItems, supplier} = order;
    const productsToOrder = orderItems.map((item, i) => {
        return <OrderItem key={i} item={item} index={i}/>
    })
    const total = orderItems.reduce(getTotal, 0)
    return (
        <Card sx={{width: 0.99}}>
            <CardHeader title={`order to ${supplier?.firstName} ${supplier?.lastName}`}/>
            <Divider/>
            <CardContent>
                <Grid container>
                    {productsToOrder}
                </Grid>
            </CardContent>
            <Divider/>
            <CardActions>
                <Grid container direction="row-reverse">
                    <Grid item>
                        <Toolbar>
                            Total:€ {total.toFixed(2)}
                        </Toolbar>
                    </Grid></Grid>
            </CardActions>
        </Card>
    )
}

export default OrderPreview;
