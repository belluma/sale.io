import React from 'react'
import { getTotal } from '../../../forms/order/helper';


//component imports

import { Card, CardActions, Container, Divider, Typography} from "@mui/material";
import OrderItem from "../../../forms/order/order-item/OrderItem";
import CardContent from "@material-ui/core/CardContent";
import Grid from "@material-ui/core/Grid";

//interface imports

import {ReactJSXElement} from "@emotion/react/types/jsx-namespace";
import {IOrderItem} from "../../../../interfaces/IOrder";

type Props = {
    children?: ReactJSXElement[],
    orderItems: IOrderItem[],
    isFormEnabled?: boolean
};

function OrderDetails({children, orderItems, isFormEnabled}: Props){
    const productsOnOrder = orderItems.map((item, i) => {
        return <OrderItem form={isFormEnabled} key={i} item={item} index={i}/>
    })
    const total = orderItems.reduce(getTotal, 0);
    return(
        <Card sx={{width: 0.99, height: 0.99, display: 'flex', flexDirection: 'column'}}>
            {children?.length && children.length > 1 && children[1]}
            {children?.length && children.length > 2 && children[2]}
            <CardContent>
                <Grid container>
                    {productsOnOrder}
                </Grid>
            </CardContent>
            <Container sx={{flexGrow: 1}}/>
            <Divider/>
            <CardActions sx={{display: "flex", justifyContent: "space-between", flexDirection:"row-reverse"}}>
                <Typography >Total:€ {total.toFixed(2)}</Typography>
                {children?.length  && children[0]}
            </CardActions>
        </Card>
    )
}

export default OrderDetails;