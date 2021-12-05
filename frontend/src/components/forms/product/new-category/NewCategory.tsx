import React from 'react'
import {Button, Grid, Paper, Popper, Typography} from "@mui/material";

//component imports

//interface imports
import {VirtualElement} from '@popperjs/core';

type Props = {
    id?: string,
    open: boolean,
    anchorEl?: any | null | VirtualElement | (() => VirtualElement);
};

function NewCategory({id, open, anchorEl}: Props){
    console.log(anchorEl)
    return(
        <Popper id={id} open={open} anchorEl={anchorEl} style={{zIndex: 1400}}>
            <Paper sx={{width: {xs: 0.5, sm: 280}, padding: 3}} elevation={24}>
                <Grid container sx={{bgcolor: 'paper'}} spacing={3}>
                    <Grid item xs={12}>
                        <Typography variant={"body2"} gutterBottom align="center" >
                            Do you really want to takeff the order?
                        </Typography>
                    </Grid>
                    <Grid item container justifyContent='space-between' xs={12}>
                        <Grid item>
                            <Button size={'small'} variant={'contained'} >Cancel</Button>
                        </Grid>
                        <Grid item>
                            <Button size={'small'} variant={'contained'} >Ok</Button>
                        </Grid>
                    </Grid>
                </Grid>
            </Paper>
        </Popper>
    )
}

export default NewCategory;
