import React from 'react'
import {IOrder} from "../../../../../../interfaces/IOrder";
import {IProduct} from "../../../../../../interfaces/IProduct";
import { List, ListItemButton, ListItemText} from "@mui/material";
import {parseName} from "../../../../../../interfaces/IThumbnailData";

//component imports

//interface imports

type Props = {
    items: IOrder[] | IProduct[]
};

function ListAccordion({items}: Props){
    const instanceOfOrder = (object: any):object is IOrder => {
        return 'name' in object;
    }
    const extractName = (item: IOrder | IProduct) => {
        //@ts-ignore typecheck with instanceOfOrder function
        return instanceOfOrder(item) ? item.name : `order to ${parseName(item.supplier)}`
    }
    const list = [...items].map((item) => <ListItemButton key={item.id} ><ListItemText primary={extractName(item)} /></ListItemButton>);
    return(
        <List>
            {list}
        </List>
    )
}

export default ListAccordion;
