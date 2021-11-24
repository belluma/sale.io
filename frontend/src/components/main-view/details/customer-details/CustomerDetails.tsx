import React from 'react'

//component imports

import OrderDetails from "../order-details/OrderDetails";
import {Button, CardHeader, Divider, Toolbar} from "@mui/material";

//interface imports

import {IOrderItem} from "../../../../interfaces/IOrder";

type Props = {};

function CustomerDetails(props: Props){
    const orderItems: IOrderItem[] = [];
    const name = "test"
    const Buttons = () => (
        <Toolbar>
            <Button>Add</Button>
            <Button>Cashout</Button>
        </Toolbar>
)

    return(
        <OrderDetails orderItems={orderItems}>
                <Buttons />
            <CardHeader title={name}/>
            <Divider/>
        </OrderDetails>
    )
}

export default CustomerDetails;
