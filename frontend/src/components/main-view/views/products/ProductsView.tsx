import React, {useEffect} from 'react'
import {useAppDispatch, useAppSelector} from '../../../../app/hooks';
import {getAllProducts, selectProducts} from "../../../../slicer/productSlice";
import {parseProductToThumbnailData, Views} from "../../../../interfaces/IThumbnailData";
import {selectView} from "../../../../slicer/viewSlice";
import {productColumns} from "../../list-view/columnDefinition";

//component imports
import ListView from "../../list-view/ListView";
import GridView from "../../grid-view/GridView";

//interface imports

type Props = {};

function ProductsView(props: Props) {
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllProducts());
    }, [dispatch]);

    const products = useAppSelector(selectProducts)
    const thumbnails = products.map(product => parseProductToThumbnailData(product));

    return useAppSelector(selectView) ?
        <GridView gridItems={thumbnails} view={Views.PRODUCT}/> :
        <ListView rows={products} columns={productColumns}/>
}

export default ProductsView;
