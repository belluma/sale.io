import React from 'react'
import {Views} from "../../../../interfaces/IThumbnail";
import {parseProductToThumbnail} from "../../thumbnail/helper";
import GridView from "../../grid-view/GridView";
import {useAppSelector} from "../../../../app/hooks";
import {selectCurrentCustomer} from "../../../../slicer/customerSlice";
import {Redirect} from "react-router";
import {selectProducts} from "../../../../slicer/productSlice";
import {selectCurrentCategory} from "../../../../slicer/categorySlice";

//component imports

//interface imports

type Props = {
};

function SalesViewProducts(props: Props){
    const category = useAppSelector(selectCurrentCategory);
    const currentCustomer = useAppSelector(selectCurrentCustomer)
    const allProducts = useAppSelector(selectProducts);
    const prductsByCategory = allProducts.filter(product => product.category === category)
    const thumbnails = prductsByCategory.map(parseProductToThumbnail)
    return(
        !currentCustomer.id ? <Redirect to={'start'}  /> : <GridView gridItems={thumbnails} view={Views.LOGIN} />
    )
}

export default SalesViewProducts;
