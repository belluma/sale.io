import React from 'react'
import {images} from "../../../helpers";
import {useAppSelector} from "../../../../../app/hooks";
import {selectDetailsData} from "../../../../../slicer/detailsSlice";

//component imports
import {Card, CardContent, CardHeader, CardMedia, Divider} from "@mui/material";
import Login from "../../../../security/login/Login";
import {useLocation} from "react-router";
import {Buttons} from "../../../../../interfaces/IThumbnailData";
import FormWrapper from "../../../../forms/_elements/form-wrapper/FormWrapper";

//interface imports

type Props = {};

function DetailsCard(props: Props){
    const detailsData = useAppSelector(selectDetailsData);
    const {title, subtitle, picture, alt, model} = detailsData;
    const path = useLocation().pathname.slice(1);
    const formSelector = Object.values(Buttons).includes(path as Buttons) ? path : "none";
    const cardContent = {
        //@ts-ignore check type in ternary statement at declaration of formSelector
        new: <FormWrapper model={formSelector}/>,
        login: <Login/>,
        employee: (<div/>),
        product: (<div/>),
        customer: (<div/>),
        supplier: (<div/>),

    }
    return(
        <Card sx={{width: 400, justifyContent: "center"}} >
            <CardHeader title={title} subtitle={subtitle} align="center"/>
            <Divider/>
            {model !== "new" && <CardMedia
                component="img"
                sx={{width: 400}}
                image={picture || images[model]}
                alt={alt}
            />}
            <CardContent>
                {cardContent[model]}
            </CardContent>
        </Card>
    )
}

export default DetailsCard;
