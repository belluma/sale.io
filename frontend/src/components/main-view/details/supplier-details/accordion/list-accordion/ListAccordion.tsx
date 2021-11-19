import React from 'react'
//component imports

import SupplierListItem from "./supplier-list-item/SupplierListItem";
import {List} from "@mui/material";
//interface imports
import {IOrder} from "../../../../../../interfaces/IOrder";
import {IProduct} from "../../../../../../interfaces/IProduct";

type Props = {
    items: IOrder[] | IProduct[]
};

function ListAccordion({items}: Props) {
    const list = [...items].map((item) => <SupplierListItem key={item.id} item={item}/>);
    return (
        <List>
            {list}
        </List>
    )
}

export default ListAccordion;
