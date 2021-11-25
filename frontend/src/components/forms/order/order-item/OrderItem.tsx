import React, {useState} from 'react'
import {useAppDispatch} from "../../../../app/hooks";
import {editItemQty} from "../../../../slicer/orderSlice";
import {getSubTotalRetail, getSubTotalWholesale} from "../helper";
//component imports
import {Grid, IconButton, Toolbar} from "@mui/material";
import ClearIcon from "@mui/icons-material/Clear"
import EditIcon from "@mui/icons-material/Edit"
import CheckIcon from "@mui/icons-material/Check"
import CustomNumber from "../../_elements/custom-number/CustomNumber";


//interface imports
import {IOrderItem} from "../../../../interfaces/IOrder";
import ConfirmDelete from "./confirm-delete/ConfirmDelete";
import {ActionCreatorWithPayload} from "@reduxjs/toolkit";

type Props = {
    item: IOrderItem,
    index: number,
    form?: boolean,
    retail?: boolean,
    handleRemove?: ActionCreatorWithPayload<number>,

};

function OrderItem({item, index, form, retail, handleRemove}: Props) {
    const dispatch = useAppDispatch();
    const [edit, setEdit] = useState(false);
    const [popperAnchor, setPopperAnchor] = React.useState<null | HTMLElement>(null);
    const popperOpen = Boolean(popperAnchor);
    const popperId = popperOpen ? 'confirm-delete-popper' : undefined;
    const openConfirmRemovePopper = (event: React.MouseEvent<HTMLButtonElement>) => {
        setPopperAnchor(event.currentTarget)
    }
    const closePopper = () => setPopperAnchor(null);
    const total = retail ? getSubTotalRetail(item) : getSubTotalWholesale(item);
    const changeQuantity = (e: React.ChangeEvent<HTMLInputElement>) => {
        dispatch(editItemQty({quantity: +e.target.value, index}));
    }
    return (
        <Toolbar sx={{width: 0.99}} disableGutters>
            <Grid item xs={2}>
                {form && <IconButton sx={{display: "inline"}} onClick={openConfirmRemovePopper} color="warning">
                    <ClearIcon/>
                </IconButton>}
            </Grid>
            <Grid item xs={5} component="h1">{item.product?.name}</Grid>
            {edit ?
                <Grid item xs={2}>
                    <CustomNumber name={"quantity"} label={"quantity"} onChange={changeQuantity} value={item.quantity}/>
                </Grid>
                : <Grid item xs={2} sx={{textAlign: "end", pr: 1}}>{item.quantity}</Grid>}
            <Grid item xs={1}>
                {form && <IconButton sx={{display: "inline"}} onClick={() => setEdit(!edit)} edge='start'>
                    {edit ? <CheckIcon/> : <EditIcon/>}
                </IconButton>}
            </Grid>
            <Grid item xs={3}>€ {total?.toFixed(2)}</Grid>
            <ConfirmDelete id={popperId} open={popperOpen} anchorEl={popperAnchor} cancel={closePopper}
                           confirm={handleRemove} itemIndex={index} name={item.product?.name}/>
        </Toolbar>
    )
}

export default OrderItem;
