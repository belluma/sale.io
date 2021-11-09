import React, {useEffect} from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import { parseSupplierToThumbnailData, Views} from "../../../../interfaces/IThumbnailData";
import {getAllSuppliers, selectSuppliers} from "../../../../slicer/supplierSlice";

//component imports
import GridView from "../../grid-view/GridView";

//interface imports

type Props = {};

function Suppliers(props: Props){
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllSuppliers());
    },[dispatch] );

    const suppliers = useAppSelector(selectSuppliers).map(supplier => parseSupplierToThumbnailData(supplier));
    return(
        <GridView gridItems={suppliers} view={Views.SUPPLIER}/>
    )
}

export default Suppliers;
