import React from 'react'
import {useAppSelector} from '../../../../app/hooks';
import {selectLoggedIn} from '../../../../slicer/authSlice';

//component imports
import {Redirect} from "react-router";
import {Card,  CardMedia, Container, Grid} from "@mui/material";

//interface imports

type Props = {};

function StartView(props: Props) {
    const loggedIn = useAppSelector(selectLoggedIn);
    return (
        !loggedIn ? <Redirect to={'/login'}/> :
            <Container maxWidth={false} color="primary">
                <Grid container sx={{height: 0.9}}  justifyContent="space-around" alignItems="center">
                    <Grid item md={3} xs={12} >
                        <Card sx={{ bgcolor:"transparent", boxShadow:"none"}} >
                            <CardMedia
                                component="img"
                                height={50}
                                src={"logo_gold.png"}/>
                        </Card>
                    </Grid>
                    <Grid item xs={12} >
                        <Card sx={{margin: "auto", width:{xs: 0.99, md: 0.2}, bgcolor:"transparent", boxShadow:"none"}} >
                            <CardMedia
                                component="img"
                                height={50}
                                src={"lettering_gold.png"}/>
                        </Card>
                    </Grid>
                </Grid></Container>
    )
}

export default StartView;
