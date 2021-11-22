import React from 'react'
import { useAppSelector } from '../../../../app/hooks';
import { selectLoggedIn } from '../../../../slicer/authSlice';

import {appBarHeight} from "../../../header/Header";
//component imports
import {Redirect} from "react-router";
import {Card, CardContent, CardMedia, Grid} from "@mui/material";
import {images} from "../../helpers";

//interface imports

type Props = {};

function StartView(props: Props){
    const loggedIn = useAppSelector(selectLoggedIn);

    // const view = ()


    return(
       !loggedIn ? <Redirect to={'/login'} /> : <Card sx={{pt: 0, height: `calc(100vh - ${appBarHeight}`}} > <CardMedia
           component="img"
           height={"100vh"}
           sx={{width: "100vw"}}
           image='/background.png'
           alt={"asdf"}
       /></Card>
    )
}

export default StartView;
