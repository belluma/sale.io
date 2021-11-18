import React, {useState} from 'react'
import {useAppDispatch} from "../../../../app/hooks";
import {editItemQty, removeOrderItem} from "../../../../slicer/orderSlice";
import {getSubTotal} from "../helper";
//component imports
import {Grid, IconButton, Toolbar} from "@mui/material";
import ClearIcon from "@mui/icons-material/Clear"
import EditIcon from "@mui/icons-material/Edit"
import CheckIcon from "@mui/icons-material/Check"
import CustomNumber from "../../_elements/custom-number/CustomNumber";


//interface imports
import {IOrderItem} from "../../../../interfaces/IOrder";

type Props = {
    item: IOrderItem,
    index: number,
    form?: boolean
};

function OrderItem({item, index, form}: Props) {
    const dispatch = useAppDispatch();
    const [edit, setEdit] = useState(false);
    const handleRemove = () => {
        dispatch(removeOrderItem(index));
    }
    const total = getSubTotal(item)
    const changeQuantity = (e:React.ChangeEvent<HTMLInputElement>) => {
        dispatch(editItemQty({quantity: +e.target.value, index}));
    }

    return (
        <Toolbar sx={{width: 0.99}} disableGutters>
            <Grid item xs={1}>
                {form && <IconButton sx={{display: "inline"}} onClick={handleRemove} color="warning">
                    <ClearIcon/>
                </IconButton>}
            </Grid>
            <Grid item xs={5} component="h1">{item.product?.name}</Grid>
            {edit ?
                <Grid item xs={2}>
                    <CustomNumber name={"quantity"} label={"quantity"} onChange={changeQuantity} value={item.quantity}/>
                </Grid>
                : <Grid item xs={2} sx={{textAlign:"end", pr:1}}>{item.quantity}</Grid>}
            <Grid item xs={1}>
                {form && <IconButton sx={{display: "inline"}} onClick={() => setEdit(!edit)} edge='start'>
                    {edit ? <CheckIcon/> : <EditIcon/>}
                </IconButton>}
            </Grid>
            <Grid item xs={3}>€ {total?.toFixed(2)}</Grid>
        </Toolbar>
    )
}

export default OrderItem;
