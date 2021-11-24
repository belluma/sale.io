import React from 'react'
import {parseProductToThumbnail} from "../../thumbnail/helper";
import {useAppSelector} from "../../../../app/hooks";
import {selectCurrentCustomer} from "../../../../slicer/customerSlice";
import {Redirect, useLocation} from "react-router";
import {selectProducts} from "../../../../slicer/productSlice";

//component imports
import GridView from "../../grid-view/GridView";

//interface imports
import {Views} from "../../../../interfaces/IThumbnail";

type Props = {
};

function SalesViewProducts(props: Props){
    const currentCustomer = useAppSelector(selectCurrentCustomer)
    const allProducts = useAppSelector(selectProducts);
    const search = useLocation().search;
    const category = new URLSearchParams(search).get('category');
    const productsByCategory = allProducts.filter(product => product.category?.name === category)
    const thumbnails = productsByCategory.map(parseProductToThumbnail)
    return(
        !currentCustomer.id ? <Redirect to={'start'}  /> : <GridView gridItems={thumbnails} view={Views.LOGIN} />
    )
}

export default SalesViewProducts;
