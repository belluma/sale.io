import React from 'react'

import {selectCurrentCustomer} from "../../../../slicer/customerSlice";
import {useAppSelector} from "../../../../app/hooks";

//component imports
import OrderDetails from "../order-details/OrderDetails";
import {Button, CardHeader, Divider, Toolbar} from "@mui/material";
import AttachMoneyIcon from '@mui/icons-material/AttachMoney'

import AddIcon from '@mui/icons-material/Add'

//interface imports
import {IOrderItem} from "../../../../interfaces/IOrder";

type Props = {};

function CustomerDetails(props: Props){
    const currentCustomer = useAppSelector(selectCurrentCustomer);
    const orderItems: IOrderItem[] = [];
    const {id, name} = currentCustomer;
    const customerName = name ? name : `table ${id}`
    const Buttons = () => (
        <Toolbar>
            <Button variant={'contained'} sx={{mx:2}} startIcon={<AddIcon/>}>Add</Button>
            <Button variant={'contained'} sx={{mx:2}} startIcon={<AttachMoneyIcon/>}>Cashout</Button>
        </Toolbar>
)

    return(
        <OrderDetails orderItems={orderItems}>
                <Buttons />
            <CardHeader title={customerName}/>
            <Divider/>
        </OrderDetails>
    )
}

export default CustomerDetails;
