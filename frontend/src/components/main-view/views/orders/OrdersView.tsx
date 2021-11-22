import React, {useEffect} from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {parseOrderToThumbnail, Views} from "../../../../interfaces/IThumbnail";
import {selectView} from "../../../../slicer/viewSlice";
import {supplierColumns} from "../../list-view/columnDefinition";
import {getAllOrders, selectOrders} from "../../../../slicer/orderSlice";

//component imports
import GridView from "../../grid-view/GridView";
import ListView from "../../list-view/ListView";
//interface imports

import {IOrder} from "../../../../interfaces/IOrder";

type Props = {};

function OrdersView(props: Props){
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllOrders());
    }, [dispatch]);

    const orders = useAppSelector(selectOrders)
    //@ts-ignore status can't be undefined because the backend automatically adds it to any order that gets created
    const byStatus = (a:IOrder, b:IOrder) =>  a.status.localeCompare(b.status)
    const thumbnails = [...orders].sort(byStatus).map(parseOrderToThumbnail);

    return (
        useAppSelector(selectView) ?
            <GridView gridItems={thumbnails} view={Views.ORDER}/> :
            <ListView rows={orders} columns={supplierColumns}/>

    )
}

export default OrdersView;
