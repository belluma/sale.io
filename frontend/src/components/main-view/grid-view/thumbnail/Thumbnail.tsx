import React from 'react'
import {useAppDispatch} from '../../../../app/hooks';
import {images} from '../../helpers'

import {setDetailData, showDetails} from "../../../../slicer/detailsSlice";
import {chooseCurrentEmployee, toBeReplaced} from "../../../../slicer/employeeSlice";
import {chooseCurrentProduct} from "../../../../slicer/productSlice";
import {chooseCurrentSupplier} from "../../../../slicer/supplierSlice";
import {chooseCurrentOrder} from "../../../../slicer/orderSlice";
//component imports

import {Card, CardActions, CardContent, CardHeader, CardMedia, Divider} from "@mui/material";
//interface imports
import {IThumbnailData, Views} from '../../../../interfaces/IThumbnailData';

type Props = {
    data: IThumbnailData
}


function Thumbnail({data}: Props) {
    const {title, subtitle, picture, id, alt, model, contentText, footerText} = data;
    const dispatch = useAppDispatch();
    const selectors = {
        none: toBeReplaced,
        login: chooseCurrentEmployee,
        employee: toBeReplaced,
        product: chooseCurrentProduct,
        customer: toBeReplaced,
        supplier: chooseCurrentSupplier,
        order: chooseCurrentOrder,
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

            {contentText && <Divider/>}
            {contentText && <CardContent>{contentText}</CardContent>}
            {footerText && <Divider/>}
            {footerText && <CardActions className="is-pulled-right">{footerText}</CardActions>}
        </Card>
    )
}


export default Thumbnail;
