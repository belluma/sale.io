import React from 'react'
import {images} from "../../helpers";
import {useAppSelector} from "../../../../app/hooks";
import {selectDetailsData} from "../../../../slicer/detailsSlice";

import {useLocation} from "react-router";
//component imports
import {Card, CardContent, CardHeader, CardMedia, Divider} from "@mui/material";
import Login from "../security/login/Login";
import FormWrapper from "../../../forms/_elements/form-wrapper/FormWrapper";
import ErrorMessage from "../../../messages/error-message/ErrorMessage";
import OrderPreview from "../../../forms/order/preview/OrderPreview";
import ProductDetails from "../product-details/ProductDetails";
import EmployeeDetails from "../employee-details/EmployeeDetails";
import SupplierDetails from "../supplier-details/SupplierDetails";

//interface imports
import {Model} from "../../../../interfaces/IThumbnail";

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
        employee: <EmployeeDetails/>,
        product: <ProductDetails/>,
        customer: <><div></div><></></>,
        supplier: <SupplierDetails/>,
        order: <OrderPreview/>,
        error: <ErrorMessage statusText={subtitle}/>,
        none: (<></>),
        newCustomer: (<></>)
    }
    const showPic = (model !== "new" && model !== "order" && model !== "supplier");
    return (
        <Card sx={{width: {sm: .99, md: 500}, height: 0.99, display: "flex", flexDirection: "column", overflow:"auto"}}>
            <CardHeader title={title} subtitle={subtitle} align="center"/>
            <Divider/>
            {showPic && <CardMedia
                component="img"
                sx={{width: .99, maxHeight: 350, alignSelf:'center'}}
                image={picture || images[model]}
                alt={alt}
                height="350"
            />}
            {showPic && <Divider/>}
            <CardContent sx={{flexGrow: 1}}>
                {cardContent[model]}
            </CardContent>
        </Card>
    )
}

export default DetailsCard;
