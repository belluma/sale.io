import React from 'react'
import {selectCurrentOrder, selectOrderToSave} from "../../../../slicer/orderSlice";
import { getTotal} from "../helper";

import {useAppSelector} from "../../../../app/hooks";
//component imports
import OrderItem from "../order-item/OrderItem";
import {Card, CardActions, CardHeader, Divider, Toolbar} from "@mui/material";
import CardContent from '@material-ui/core/CardContent';

import Grid from '@material-ui/core/Grid';
//interface imports

type Props = {
    form?:boolean,
};

function OrderPreview({form}: Props) {
    const orderToSave = useAppSelector(selectOrderToSave)
    const currentOrder = useAppSelector(selectCurrentOrder);
    const order = form ? orderToSave : currentOrder;
    const {orderItems, supplier} = order;
    const productsToOrder = orderItems.map((item, i) => {
        return <OrderItem key={i} item={item} index={i}/>
    })
    const total = orderItems.reduce(getTotal, 0)
    return (
        <Card sx={{width: 0.99}}>
            {form && <CardHeader title={`order to ${supplier?.firstName} ${supplier?.lastName}`}/>}
            {form && <Divider/>}
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
