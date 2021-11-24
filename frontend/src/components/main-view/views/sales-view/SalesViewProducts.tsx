import React from 'react'
import {parseProductToThumbnail} from "../../thumbnail/helper";
import {useAppSelector} from "../../../../app/hooks";
import {selectCurrentCustomer} from "../../../../slicer/customerSlice";
import {selectProducts} from "../../../../slicer/productSlice";


//component imports

import {Redirect} from "react-router";
import GridView from "../../grid-view/GridView";

//interface imports
import {Views} from "../../../../interfaces/IThumbnail";
import {selectCurrentCategory} from "../../../../slicer/categorySlice";

type Props = {
};

function SalesViewProducts(props: Props){
    const currentCustomer = useAppSelector(selectCurrentCustomer)
    const allProducts = useAppSelector(selectProducts);
    const category = useAppSelector(selectCurrentCategory);
    const productsByCategory = allProducts.filter(product => product.category?.id === category.id)
    const thumbnails = productsByCategory.map(parseProductToThumbnail)
    return(
        !currentCustomer.id ? <Redirect to={'start'}  /> : <GridView gridItems={thumbnails} view={Views.LOGIN} />
    )
}

export default SalesViewProducts;
