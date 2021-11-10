import React, {useEffect} from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {parseSupplierToThumbnailData, Views} from "../../../../interfaces/IThumbnailData";
import {getAllSuppliers, selectSuppliers} from "../../../../slicer/supplierSlice";
import {supplierColumns} from "../../list-view/columnDefinition";
import {selectView} from "../../../../slicer/viewSlice";

//component imports
import GridView from "../../grid-view/GridView";
import ListView from "../../list-view/ListView";

//interface imports

type Props = {};

function Suppliers(props: Props) {
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllSuppliers());
    }, [dispatch]);

    const suppliers = useAppSelector(selectSuppliers)
    const thumbnails = suppliers.map(supplier => parseSupplierToThumbnailData(supplier));

    return (
        useAppSelector(selectView) ?
            <GridView gridItems={thumbnails} view={Views.SUPPLIER}/> :
            <ListView rows={suppliers} columns={supplierColumns}/>

    )
}

export default Suppliers;
