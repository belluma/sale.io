import React from 'react'
import {getTotalRetail, getTotalWholeSale} from '../../../forms/order/helper';


//component imports

import { Card, CardActions, Container, Divider, Typography} from "@mui/material";
import OrderItem from "../../../forms/order/order-item/OrderItem";
import CardContent from "@material-ui/core/CardContent";
import Grid from "@material-ui/core/Grid";

//interface imports

import {ReactJSXElement} from "@emotion/react/types/jsx-namespace";
import {IOrderItem} from "../../../../interfaces/IOrder";
import {dividerStyles} from "../styles";

type Props = {
    children?: ReactJSXElement[],
    orderItems: IOrderItem[],
    isFormEnabled?: boolean,
    retail?: boolean
};
function OrderDetails({children, orderItems, isFormEnabled, retail}: Props){
    const productsOnOrder = orderItems.map((item, i) => {
        return <OrderItem form={isFormEnabled} key={i} item={item} retail={retail} index={i}/>
    })
    const total = orderItems.reduce(retail ? getTotalRetail : getTotalWholeSale, 0);
    return(
        <Card sx={{width: 1, height: 1, display: 'flex', flexDirection: 'column'}}>
            {children?.length && children.length > 1 && children[1]}
            {children?.length && children.length > 2 && children[2]}
            <CardContent >
                <Grid  container>
                    {productsOnOrder}
                </Grid>
            </CardContent>
            <Container sx={{flexGrow: 1}}/>
            < Divider sx={dividerStyles}/>
            <CardActions sx={{display: "flex", justifyContent: "space-between", flexDirection:"row-reverse"}}>
                <Typography >Total:€ {total.toFixed(2)}</Typography>
                {children?.length  && children[0]}
            </CardActions>
        </Card>
    )
}

export default OrderDetails;
