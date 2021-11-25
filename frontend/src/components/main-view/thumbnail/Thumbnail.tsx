import React from 'react'
import {useAppDispatch} from '../../../app/hooks';
import {images, thumbnailStyles} from '../helpers'

import {setDetailData, showDetails} from "../../../slicer/detailsSlice";
import {chooseCurrentEmployee} from "../../../slicer/employeeSlice";
import {chooseCurrentProduct} from "../../../slicer/productSlice";
import {chooseCurrentSupplier} from "../../../slicer/supplierSlice";
import {chooseCurrentOrder} from "../../../slicer/orderSlice";
//component imports
import {Card, CardActions, CardContent, CardHeader, CardMedia, Divider} from "@mui/material";
//interface imports
import {IThumbnail, Views} from '../../../interfaces/IThumbnail';
import {chooseCurrentCustomer, createCustomer} from "../../../slicer/customerSlice";
import {chooseCurrentCategory} from "../../../slicer/categorySlice";

type Props = {
    data: IThumbnail
}


function Thumbnail({data}: Props) {
    const {title, subtitle, picture, id, alt, model, contentText, footerText} = data;
    const dispatch = useAppDispatch();
    const selectors = {
        none: chooseCurrentCategory,
        login: chooseCurrentEmployee,
        employee: chooseCurrentEmployee,
        product: chooseCurrentProduct,
        customer: chooseCurrentCustomer,
        newCustomer: createCustomer,
        supplier: chooseCurrentSupplier,
        order: chooseCurrentOrder,
    }
    const onClick = () => {
        if(model !== Views.NEWCUSTOMER && model !== Views.NONE) {
            dispatch(setDetailData(data));
            dispatch(showDetails());
        }
        if (model !== Views.NEW && model !== Views.ERROR && id) {
            dispatch(selectors[model](id));
        }
    }
    return (
        <Card onClick={onClick} sx={thumbnailStyles}>
            <CardHeader title={title} subtitle={subtitle}/>
            <CardMedia
                sx={{flexGrow: 1, maxHeight: 220}}
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
