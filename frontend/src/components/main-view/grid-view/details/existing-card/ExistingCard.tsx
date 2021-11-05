import React from 'react'
import {images} from "../../../helpers";
import {useAppSelector} from "../../../../../app/hooks";
import {selectDetailsData} from "../../../../../slicer/detailsSlice";

//component imports
import {Card, CardContent, CardHeader, CardMedia, Divider} from "@mui/material";
import Login from "../../../../security/login/Login";
import CustomForm from "../../../../custom-form/CustomForm";

//interface imports

type Props = {};

function ExistingCard(props: Props){
    const detailsData = useAppSelector(selectDetailsData);
    const {title, subtitle, picture, alt, model} = detailsData;
    const cardContent = {
        new: <CustomForm />,
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

export default ExistingCard;
