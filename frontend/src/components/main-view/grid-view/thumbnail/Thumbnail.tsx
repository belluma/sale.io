import React from 'react'
import {useAppDispatch} from '../../../../app/hooks';
import {images} from '../../helpers'

//component imports
import {Card, CardHeader, CardMedia} from "@mui/material";

//interface imports
import {IThumbnailData, Views} from '../../../../interfaces/IThumbnailData';
import {setDetailData, showDetails} from "../../../../slicer/detailsSlice";
import {chooseCurrentEmployee, toBeReplaced} from "../../../../slicer/employeeSlice";
import {chooseCurrentProduct} from "../../../../slicer/productSlice";
import {chooseCurrentSupplier} from "../../../../slicer/supplierSlice";

type Props = {
    data: IThumbnailData
}


function Thumbnail({data}: Props) {
    const {title, subtitle, picture, id, alt, model} = data;
    const dispatch = useAppDispatch();
    const selectors = {
        none: toBeReplaced,
        login: chooseCurrentEmployee,
        employee: toBeReplaced,
        product: chooseCurrentProduct,
        customer: toBeReplaced,
        supplier: chooseCurrentSupplier,
        order: chooseCurrentProduct,
    }
    const onClick = () => {
        dispatch(setDetailData(data));
        dispatch(showDetails());
        if (model !== Views.NEW && model !== Views.ERROR && id) dispatch(selectors[model](id))
    }

    return (
        <Card onClick={onClick} sx={{height: 500, width: 345}}>
            <CardHeader title={title} subtitle={subtitle}/>
            <CardMedia
                component="img"
                height="350"
                image={picture || images[model]}
                alt={alt}
            />
        </Card>
    )
}


export default Thumbnail;
