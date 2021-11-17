import React, {useEffect} from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {parseOrderToThumbnailData, Views} from "../../../../interfaces/IThumbnailData";
import {selectView} from "../../../../slicer/viewSlice";
import GridView from "../../grid-view/GridView";
import ListView from "../../list-view/ListView";
import {supplierColumns} from "../../list-view/columnDefinition";
import {getAllOrders, selectOrders} from "../../../../slicer/orderSlice";
import {IOrder} from "../../../../interfaces/IOrder";

//component imports

//interface imports

type Props = {};

function OrdersView(props: Props){
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllOrders());
    }, [dispatch]);

    const orders = useAppSelector(selectOrders)
    //@ts-ignore status can't be undefined becasue the backend automatically adds it to any order that gets created
    const byStatus = (a:IOrder, b:IOrder) =>  a.status.localeCompare(b.status)
    const thumbnails = [...orders].sort(byStatus).map(parseOrderToThumbnailData);

    return (
        useAppSelector(selectView) ?
            <GridView gridItems={thumbnails} view={Views.ORDER}/> :
            <ListView rows={orders} columns={supplierColumns}/>

    )
}

export default OrdersView;
