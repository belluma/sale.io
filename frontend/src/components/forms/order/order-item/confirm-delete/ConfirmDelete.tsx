import React from 'react'
import {ActionCreatorWithPayload} from "@reduxjs/toolkit";
import {useAppDispatch} from "../../../../../app/hooks";
//component imports
import {Button, Grid, Paper, Popper, Typography} from "@mui/material";

//interface imports
import {VirtualElement} from '@popperjs/core';

type Props = {
    id?: string,
    open: boolean,
    anchorEl?: null | VirtualElement | (() => VirtualElement);
    cancel: () => void,
    confirm?: ActionCreatorWithPayload<number>,
    itemIndex: number,
    name?: string
};

function ConfirmDelete({id, open, anchorEl, cancel, confirm, itemIndex, name}: Props) {
    const dispatch = useAppDispatch();
    const removeItem = () => {
        confirm && dispatch(confirm(itemIndex));
    }
    return (
        <Popper id={id} open={open} anchorEl={anchorEl} style={{zIndex: 1400}}>
            <Paper sx={{width: {xs: 0.5, sm: 300}, padding: 3}} elevation={24}>
                <Grid container sx={{bgcolor: 'paper'}} spacing={3}>
                    <Grid item xs={12}>
                        <Typography variant={"body2"} gutterBottom align="center" >
                            Do you really want to take {name} from the order?
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
