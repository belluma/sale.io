import React, {useEffect} from 'react'
import {useAppDispatch, useAppSelector} from '../../../../app/hooks';
import {getAllProducts, selectProducts} from "../../../../slicer/productSlice";
import {Views} from "../../../../interfaces/IThumbnail";
import {selectView} from "../../../../slicer/viewSlice";
import {productColumns} from "../../list-view/columnDefinition";

//component imports
import ListView from "../../list-view/ListView";
import GridView from "../../grid-view/GridView";
import {parseProductToThumbnail} from "../../thumbnail/helper";
import {parseProduct} from "../../list-view/parseData";

//interface imports

type Props = {};

function ProductsView(props: Props) {
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllProducts());
    }, [dispatch]);

    const products = useAppSelector(selectProducts)
    const thumbnails = products.map(parseProductToThumbnail);
    const productRows = products.map(parseProduct)
    return useAppSelector(selectView) ?
        <GridView gridItems={thumbnails} view={Views.PRODUCT}/> :
        <ListView rows={productRows} columns={productColumns}/>
}

export default ProductsView;
