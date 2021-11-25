import React from 'react'
import { ActionCreatorWithPayload} from "@reduxjs/toolkit";
import {useAppDispatch} from "../../../../../app/hooks";
//component imports
import {Button, Popper, Typography} from "@mui/material";

//interface imports
import {VirtualElement} from '@popperjs/core';

type Props = {
    id?:string,
    open: boolean,
    anchorEl?: null | VirtualElement | (() => VirtualElement);
    cancel: () => void,
    confirm: ActionCreatorWithPayload< number>,
    itemIndex: number
};

function ConfirmDelete({id, open, anchorEl, cancel, confirm, itemIndex}: Props){
    const dispatch = useAppDispatch();
    const removeItem = () => {
         dispatch(confirm(itemIndex));
    }
    return(
      <Popper id={id} open={open} anchorEl={anchorEl} style={{zIndex: 1400}}>
      <Typography variant={"body2"} component={"p"}>Do you really want to take from the order?</Typography>
          <Button size={'small'} variant={'contained'} onClick={cancel}>Cancel</Button>
          <Button size={'small'} variant={'contained'} onClick={removeItem}>Ok</Button>
      </Popper>
    )
}

export default ConfirmDelete;
