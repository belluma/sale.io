import React from 'react'
import {Card, CardContent, CardHeader, CardMedia, Divider} from "@mui/material";
import {images} from "../../../helpers";
import {useAppSelector} from "../../../../../app/hooks";
import {selectDetailsData} from "../../../../../slicer/detailsSlice";
import Login from "../../../../security/login/Login";

//component imports

//interface imports

type Props = {};

function ExistingCard(props: Props){
    const detailsData = useAppSelector(selectDetailsData);
    const {title, subtitle, picture, alt, model} = detailsData;
    const cardContent = {
        none: (<div/>),
        login: <Login/>,
        employees: (<div/>),
        products: (<div/>),
        customers: (<div/>),
        suppliers: (<div/>),

    }
    return(
        <Card sx={{width: 400, justifyContent: "center"}} >
            <CardHeader title={title} subtitle={subtitle} align="center"/>
            <Divider/>
            <CardMedia
                component="img"
                sx={{width: 400}}
                image={picture || images[model]}
                alt={alt}
            />
            <CardContent>
                {cardContent[model]}
            </CardContent>
        </Card>
    )
}

export default ExistingCard;
