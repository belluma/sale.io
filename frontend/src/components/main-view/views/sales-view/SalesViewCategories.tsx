import React, {useEffect} from 'react'
import {parseCategoryToThumbnail} from "../../thumbnail/helper";
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {selectCurrentCustomer} from "../../../../slicer/customerSlice";
import {getAllCategories, selectCategories} from "../../../../slicer/categorySlice";

//component imports
import {Redirect} from "react-router";
import GridView from "../../grid-view/GridView";

//interface imports
import {Views} from "../../../../interfaces/IThumbnail";
import {getAllProducts} from "../../../../slicer/productSlice";

type Props = {};

function SalesViewCategories(props: Props){
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllCategories());
        dispatch(getAllProducts());
    }, [dispatch])
    const currentCustomer = useAppSelector(selectCurrentCustomer)
    const categories = useAppSelector(selectCategories)
    const thumbnails = categories.map(parseCategoryToThumbnail)
    return(
        !currentCustomer.id ? <Redirect to={'start'}  /> : <GridView gridItems={thumbnails} view={Views.LOGIN} />
    )
}

export default SalesViewCategories;
