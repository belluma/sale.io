import React from 'react'
import {Grid, ListItemButton} from "@mui/material";
import {IOrder} from "../../../../../../../interfaces/IOrder";
import {IProduct} from "../../../../../../../interfaces/IProduct";
import {parseName} from "../../../../../../../interfaces/IThumbnailData";
import {getTotal} from "../../../../../../forms/order/helper";
import { useHistory } from 'react-router';
import { useAppDispatch } from '../../../../../../../app/hooks';

//component imports

//interface imports

type Props = {
    item: IOrder | IProduct,
};

function SupplierListItem({item}: Props) {
    const history = useHistory();
    const dispatch = useAppDispatch();
    const isProduct = 'name' in item;
    const extractName = ():string => {
        //@ts-ignore type check with instanceOfProduct function
        return isProduct ?  item.name : `order to ${parseName(item.supplier)}`
    }
    const checkStock = (product: IProduct):string => {
        return product.amountInStock ? 'in stock' : 'out of stock';
    }
    const label = (): string => {
        return isProduct ? checkStock(item): 'total';
    }
    const quickInfo = ():string => {
        //@ts-ignore type check with instanceOfProduct function
        return isProduct ? item.amountInStock || "" : `€${item.orderItems.reduce(getTotal, 0).toFixed(2)}`;
    }
    const showItemDetails = () => {
        history.push(isProduct ? 'product' : 'supplier')

    }
    return (
        <ListItemButton component="div">
            <Grid container sx={{width:0.99}} >
                <Grid item xs={7} >{extractName()}</Grid>
                <Grid item xs={5} container justifyContent={"space-between"}>
                <Grid item>{label()}</Grid>
                <Grid item className={'is-pulled-right'}>{quickInfo()}</Grid>
                </Grid>
            </Grid>
        </ListItemButton>
    )
}

export default SupplierListItem;
