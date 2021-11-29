import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../../app/hooks";
import {removeOrderItem} from "../../../../../slicer/orderSlice";
import {selectCurrentCustomer, takeItemsOffOrder} from "../../../../../slicer/customerSlice";
//component imports

import {Button, Grid, Paper, Popper, Typography} from "@mui/material";
//interface imports
import {VirtualElement} from '@popperjs/core';

type Props = {
    id?: string,
    open: boolean,
    anchorEl?: null | VirtualElement | (() => VirtualElement);
    cancel: () => void,
    customer?:boolean,
    itemIndex: number,
    name?: string
};

function ConfirmDelete({id, open, anchorEl, cancel, customer, itemIndex, name}: Props) {
    const dispatch = useAppDispatch();
    const currentCustomer = useAppSelector(selectCurrentCustomer)
    const removeItem = () => {
        dispatch(customer ? takeItemsOffOrder(currentCustomer.orderItems[itemIndex]) : removeOrderItem(itemIndex));
    }
    return (
        <Popper id={id} open={open} anchorEl={anchorEl} style={{zIndex: 1400}}>
            <Paper sx={{width: {xs: 0.5, sm: 280}, padding: 3}} elevation={24}>
                <Grid container sx={{bgcolor: 'paper'}} spacing={3}>
                    <Grid item xs={12}>
                        <Typography variant={"body2"} gutterBottom align="center" >
                            Do you really want to take {name} off the order?
                        </Typography>
                    </Grid>
                    <Grid item container justifyContent='space-between' xs={12}>
                        <Grid item>
                            <Button size={'small'} variant={'contained'} onClick={cancel}>Cancel</Button>
                        </Grid>
                        <Grid item>
                            <Button size={'small'} variant={'contained'} onClick={removeItem}>Ok</Button>
                        </Grid>
                    </Grid>
                </Grid>
            </Paper>
        </Popper>
    )
}

export default ConfirmDelete;
