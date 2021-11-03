import React from 'react'
import {useAppDispatch} from '../../../../app/hooks';
import {images} from '../helpers'

//component imports
import {Card, CardHeader, CardMedia} from "@mui/material";

//interface imports
import {IDetailsData, Model} from '../../../../interfaces/IThumbnailData';
import {setDetailData, showDetails} from "../../../../slicer/detailsSlice";
import {chooseCurrentEmployee} from "../../../../slicer/employeeSlice";

type Props = {
    data: IDetailsData
}


function Thumbnail({data}: Props) {
    const {title, subtitle, picture, id, alt, model} = data;
    const dispatch = useAppDispatch();
    const selectors = {
        login: chooseCurrentEmployee,
    }
    const onClick = () => {
        dispatch(setDetailData(data));
        dispatch(showDetails());
        if (model !== Model.NONE && id) dispatch(selectors[model](id))
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
