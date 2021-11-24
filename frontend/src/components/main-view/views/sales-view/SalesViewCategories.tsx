import React from 'react'
import {Views} from "../../../../interfaces/IThumbnail";
import {parseCategoryToThumbnail} from "../../thumbnail/helper";
import GridView from "../../grid-view/GridView";
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {selectCurrentCustomer} from "../../../../slicer/customerSlice";
import {Redirect} from "react-router";
import {getAllCategories, selectCategories} from "../../../../slicer/categorySlice";

//component imports

//interface imports

type Props = {};

function SalesViewCategories(props: Props){
    const dispatch = useAppDispatch();
    dispatch(getAllCategories());
    const currentCustomer = useAppSelector(selectCurrentCustomer)
    const categories = useAppSelector(selectCategories)
    const thumbnails = categories.map(parseCategoryToThumbnail)
    return(
        !currentCustomer.id ? <Redirect to={'start'}  /> : <GridView gridItems={thumbnails} view={Views.LOGIN} />
    )
}

export default SalesViewCategories;
