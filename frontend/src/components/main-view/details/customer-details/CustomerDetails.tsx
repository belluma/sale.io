import React from 'react'

import {hideDetails} from "../../../../slicer/detailsSlice";
import {selectCurrentCustomer} from "../../../../slicer/customerSlice";

import {useHistory} from "react-router";
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
//component imports
import OrderDetails from "../order-details/OrderDetails";

import {Button, CardHeader, Divider, Toolbar} from "@mui/material";

import AttachMoneyIcon from '@mui/icons-material/AttachMoney'
import AddIcon from '@mui/icons-material/Add'
//interface imports

type Props = {};

function CustomerDetails(props: Props){
    const history = useHistory();
    const dispatch = useAppDispatch();
    const currentCustomer = useAppSelector(selectCurrentCustomer);
    const {orderItems} = currentCustomer;
    const {id, name} = currentCustomer;
    const customerName = name ? name : `table ${id}`
    const openSalesView = () => {
        history.push('sales');
        dispatch(hideDetails());
    }
    const Buttons = () => (
        <Toolbar>
            <Button variant={'contained'} size={'small'} sx={{mr:1}} startIcon={<AddIcon/>} onClick={openSalesView}>Add</Button>
            <Button variant={'contained'} size={'small'} sx={{ml:1}} startIcon={<AttachMoneyIcon/>}>Cashout</Button>
        </Toolbar>
)

    return(
        <OrderDetails orderItems={orderItems} retail isFormEnabled customer>
                <Buttons />
            <CardHeader title={customerName}/>
            <Divider/>
        </OrderDetails>
    )
}

export default CustomerDetails;
