import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {hideDetails, selectDetailsData, selectShowDetails} from "../../../../slicer/detailsSlice";
import {images} from '../helpers'

//component imports
import {Card, CardContent, CardHeader, CardMedia, Dialog, Divider} from "@mui/material";
import Login from "../../../security/login/Login";

//interface imports

type Props = {};



function Details(props: Props) {

    const dispatch = useAppDispatch();
    const showDetails = useAppSelector(selectShowDetails);
    const detailsData = useAppSelector(selectDetailsData);
    const {title, subtitle, picture, id, alt, model} = detailsData;

    const handleClose = () => {
        dispatch(hideDetails());
    }
    const cardContent = {
        none: (<div/>),
        login: <Login />,

    }
    return (
        <Dialog open={showDetails} onClose={handleClose}>
            <Card sx={{width:400 ,justifyContent:"center"}}>
                <CardHeader title={title} subtitle={subtitle} align="center"/>
                <Divider/>
                <CardMedia
                    component="img"
                    sx={{width:400}}
                    image={picture || images[model]}
                    alt={alt}
                />
                <CardContent>
                    {cardContent[model]}
                </CardContent>
            </Card>
        </Dialog>
    )
}

export default Details;
