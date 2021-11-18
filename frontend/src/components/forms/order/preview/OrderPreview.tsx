import React from 'react'
import {receiveOrder, selectCurrentOrder, selectOrderToSave} from "../../../../slicer/orderSlice";
import {getTotal} from "../helper";

import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
//component imports
import OrderItem from "../order-item/OrderItem";
import {
    Button,
    Card,
    CardActions,
    CardHeader,
    Container,
    createTheme,
    Divider,
    ThemeOptions,
    ThemeProvider,
    Typography
} from "@mui/material";
import CardContent from '@material-ui/core/CardContent';

import Grid from '@material-ui/core/Grid';
import {parseName} from "../../../../interfaces/IThumbnailData";
import {OrderStatus} from "../../../../interfaces/OrderStatus";
import {IOrder} from "../../../../interfaces/IOrder";
//interface imports

type Props = {
    isFormEnabled?: boolean,
};

function OrderPreview({isFormEnabled}: Props) {
    const dispatch = useAppDispatch();
    const orderToSave = useAppSelector(selectOrderToSave)
    const currentOrder = useAppSelector(selectCurrentOrder);
    const order:IOrder = isFormEnabled ? orderToSave : currentOrder;
    const {orderItems, supplier} = order;
    const productsToOrder = orderItems.map((item, i) => {
        return <OrderItem form={isFormEnabled} key={i} item={item} index={i}/>
    })
    const handleReceive = () => {
        if(order.status === OrderStatus.PENDING)dispatch(receiveOrder(order));
    }
    const greenButton = (): ThemeOptions => {
        return createTheme({
            palette: {
                primary: {
                    main: "#388e3c"
                },
            }
        })
    }
    const total = orderItems.reduce(getTotal, 0)
    return (
        <Card sx={{width: 0.99, height: 0.99, display: 'flex', flexDirection: 'column'}}>
            {isFormEnabled && <CardHeader title={`order to ${supplier && parseName(supplier)}`}/>}
            {isFormEnabled && <Divider/>}
            <CardContent>
                <Grid container>
                    {productsToOrder}
                </Grid>
            </CardContent>
            <Container sx={{flexGrow: 1}}/>
            <Divider/>
            <CardActions sx={{display: "flex", justifyContent: "space-between", flexDirection:"row-reverse"}}>
                <Typography >Total:€ {total.toFixed(2)}</Typography>
                <ThemeProvider theme={greenButton()}>
                    {!isFormEnabled && order.status === OrderStatus.PENDING &&  <Button onClick={handleReceive} variant="contained">Receive</Button>}
                </ThemeProvider>
            </CardActions>
        </Card>
    )
}

export default OrderPreview;
