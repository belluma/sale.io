import React from 'react'
import {useAppSelector} from "../../../../app/hooks";
import {Views} from "../../../../interfaces/IThumbnail";
import {selectSuppliers} from "../../../../slicer/supplierSlice";
import {supplierColumns} from "../../list-view/columnDefinition";
import {selectView} from "../../../../slicer/viewSlice";

//component imports
import GridView from "../../grid-view/GridView";
import ListView from "../../list-view/ListView";
import {parseSupplierToThumbnail} from "../../thumbnail/helper";

//interface imports

type Props = {};

function SuppliersView(props: Props) {
    const suppliers = useAppSelector(selectSuppliers)
    const thumbnails = suppliers.map(supplier => parseSupplierToThumbnail(supplier));

    return (
        useAppSelector(selectView) ?
            <GridView gridItems={thumbnails} view={Views.SUPPLIER}/> :
            <ListView rows={suppliers} columns={supplierColumns}/>

    )
}

export default SuppliersView;
