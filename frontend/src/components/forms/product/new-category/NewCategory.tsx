import React, {ChangeEvent, useState} from 'react'
import {Button, Grid, Paper, Popper, Typography} from "@mui/material";

//component imports

//interface imports
import {VirtualElement} from '@popperjs/core';
import CustomText from "../../_elements/custom-text/CustomText";
import {useAppDispatch} from "../../../../app/hooks";
import {createCategory} from "../../../../slicer/categorySlice";

type Props = {
    id?: string,
    open: boolean,
    anchorEl?: null | VirtualElement | (() => VirtualElement),
    close: () => void
}

function NewCategory({id, open, anchorEl, close}: Props) {
    const dispatch = useAppDispatch();
    const [category, setCategory] = useState("")
    const handleInput = (e: ChangeEvent<HTMLInputElement>) => {
        setCategory(e.target.value)
    }
    const saveCategory = () => {
        dispatch(createCategory(category));
        close();
    }
    return (
        <Popper id={id} open={open} anchorEl={anchorEl} style={{zIndex: 1400}}>
            <Paper sx={{width: {xs: 0.5, sm: 280}, padding: 3}} elevation={24}>
                <Grid container sx={{bgcolor: 'paper'}} spacing={3}>
                    <Grid item xs={12}>
                        <Typography variant={"body2"} gutterBottom align="center">Type the name of the new
                            category</Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <CustomText name={"category"} label={"name"} model={"category"} value={category} onChange={handleInput}/>
                    </Grid>
                    <Grid item container justifyContent='space-between' xs={12}>
                        <Grid item>
                            <Button size={'small'} variant={'contained'} onClick={close}>Cancel</Button>
                        </Grid>
                        <Grid item>
                            <Button size={'small'} variant={'contained'} onClick={saveCategory}>Ok</Button>
                        </Grid>
                    </Grid>
                </Grid>
            </Paper>
        </Popper>
    )
}

export default NewCategory;
