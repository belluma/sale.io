import React from 'react'
import {useAppSelector} from '../../../../app/hooks';
import {selectProducts} from "../../../../slicer/productSlice";
import {selectView} from "../../../../slicer/viewSlice";
import {parseProductToThumbnail} from "../../thumbnail/helper";
import {parseProduct} from "../../list-view/parseData";
import {productColumns} from "../../list-view/columnDefinition";


//component imports

import ListView from "../../list-view/ListView";
import GridView from "../../grid-view/GridView";

//interface imports
import {Views} from "../../../../interfaces/IThumbnail";

type Props = {};

function ProductsView(props: Props) {
    const products = useAppSelector(selectProducts)
    const thumbnails = products.map(parseProductToThumbnail);
    const productRows = products.map(parseProduct)
    return useAppSelector(selectView) ?
        <GridView gridItems={thumbnails} view={Views.PRODUCT}/> :
        <ListView rows={productRows} columns={productColumns}/>
}

export default ProductsView;
