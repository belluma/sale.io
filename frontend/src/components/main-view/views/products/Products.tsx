import React from 'react'
import { useAppSelector } from '../../../../app/hooks';
import GridView from "../../grid-view/GridView";
import {selectProducts} from "../../../../slicer/productSlice";
import {parseProductToThumbnailData, Views} from "../../../../interfaces/IThumbnailData";

//component imports

//interface imports

type Props = {};

function Products(props: Props){
    const products = useAppSelector(selectProducts).map(product => parseProductToThumbnailData(product));
    return(
     <GridView gridItems={products} view={Views.PRODUCT}/>
    )
}

export default Products;
