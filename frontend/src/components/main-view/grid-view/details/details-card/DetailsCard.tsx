import React from 'react'
import {images} from "../../../helpers";
import {useAppSelector} from "../../../../../app/hooks";
import {selectDetailsData} from "../../../../../slicer/detailsSlice";

//component imports
import {Card, CardContent, CardHeader, CardMedia, Divider} from "@mui/material";
import Login from "../security/login/Login";
import {useLocation} from "react-router";
import {Model} from "../../../../../interfaces/IThumbnailData";
import FormWrapper from "../../../../forms/_elements/form-wrapper/FormWrapper";
import ErrorMessage from "../error-message/ErrorMessage";
import Pending from "../pending/Pending";

//interface imports

type Props = {
    fullScreen: boolean,
    handleClose: () => void
};

function DetailsCard({fullScreen, handleClose}: Props) {
    const detailsData = useAppSelector(selectDetailsData);
    const {title, subtitle, picture, alt, model} = detailsData;
    const path = useLocation().pathname.slice(1);
    const formSelector = Object.values(Model).includes(path as Model) ? path : "none";
    const cardContent = {
        //@ts-ignore check type in ternary statement at declaration of formSelector
        new: <FormWrapper fullScreen={fullScreen} handleClose={handleClose} model={formSelector}/>,
        login: <Login/>,
        employee: (<div/>),
        product: (<div/>),
        customer: (<div/>),
        supplier: (<div/>),
        order: <div/>,
        error: <ErrorMessage statusText={subtitle}/>,
    }
    return (
        <Card sx={{width: {sm: .99}, height:{sm:0.5}}}>
            <CardHeader title={title} subtitle={subtitle} align="center"/>
            <Divider/>
            {model !== "new" && <CardMedia
                component="img"
                sx={{width: .99}}
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
