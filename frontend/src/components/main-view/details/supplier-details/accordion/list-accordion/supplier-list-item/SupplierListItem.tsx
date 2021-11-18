import React from 'react'
import {Grid, ListItemButton} from "@mui/material";
import {IOrder} from "../../../../../../../interfaces/IOrder";
import {IProduct} from "../../../../../../../interfaces/IProduct";
import {parseName} from "../../../../../../../interfaces/IThumbnailData";
import {getTotal} from "../../../../../../forms/order/helper";

//component imports

//interface imports

type Props = {
    item: IOrder | IProduct,
};

function SupplierListItem({item}: Props) {
    const instanceOfProduct = (object: IProduct | IOrder): boolean => {
        return 'name' in object;
    }
    const extractName = ():string => {
        //@ts-ignore type check with instanceOfProduct function
        return instanceOfProduct(item) ?  item.name : `order to ${parseName(item.supplier)}`
    }
    const label = (): string => {
        return instanceOfProduct(item) ? 'in stock' : 'total';
    }
    const quickInfo = ():string => {
        //@ts-ignore type check with instanceOfProduct function
        return instanceOfProduct(item) ? `${item.amountInStock}` : `€${item.orderItems.reduce(getTotal, 0).toFixed(2)}`;
    }
    return (
        <ListItemButton component="a">
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
