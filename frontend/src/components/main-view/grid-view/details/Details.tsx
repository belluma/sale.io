import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {hideDetails, selectDetailsData, selectShowDetails} from "../../../../slicer/detailsSlice";

//component imports
import {Card, CardContent, CardHeader, CardMedia, Dialog, Divider, Typography} from "@mui/material";
import Login from "../../../security/login/Login";

//interface imports

type Props = {};



function Details(props: Props) {

    const dispatch = useAppDispatch();
    const showDetails = useAppSelector(selectShowDetails);
    const detailsData = useAppSelector(selectDetailsData);
    const {title, subtitle, picture, id, alt} = detailsData;

    const handleClose = () => {
        dispatch(hideDetails());
    }
    return (
        <Dialog open={showDetails} onClose={handleClose}>
            <Card>
                <CardHeader title={title} subtitle={subtitle} align="center"/>
                <Divider/>
                <CardMedia
                    component="img"
                    height="194"
                    image={picture}
                    alt={alt}
                />
                <CardContent>
                    <Login/>
                </CardContent>
            </Card>
        </Dialog>
    )
}

export default Details;
