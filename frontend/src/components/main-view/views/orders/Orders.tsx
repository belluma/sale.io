import React, {useEffect} from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {getAllSuppliers, selectSuppliers} from "../../../../slicer/supplierSlice";
import {parseSupplierToThumbnailData, Views} from "../../../../interfaces/IThumbnailData";
import {selectView} from "../../../../slicer/viewSlice";
import GridView from "../../grid-view/GridView";
import ListView from "../../list-view/ListView";
import {supplierColumns} from "../../list-view/columnDefinition";
import {getAllOrders, selectOrders} from "../../../../slicer/orderSlice";

//component imports

//interface imports

type Props = {};

function Orders(props: Props){
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllOrders());
    }, [dispatch]);

    const orders = useAppSelector(selectOrders)
    const thumbnails = orders.map(order => parseSupplierToThumbnailData(order));

    return (
        useAppSelector(selectView) ?
            <GridView gridItems={thumbnails} view={Views.ORDER}/> :
            <ListView rows={orders} columns={supplierColumns}/>

    )
}

export default Orders;
