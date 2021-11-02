import React from 'react'
import Signup from "../security/signup/Signup";
import {Container, Grid} from "@mui/material";
import {Route, Switch} from "react-router";
import LoginForm from "../security/login/Login";
import ErrorMessage from "../error-message/ErrorMessage";

//component imports

//interface imports

type Props = {};

function MainView(props: Props){
    return (
        <Container sx={{pt: 15}} maxWidth={false}>
            <Grid container justifyContent="center" alignItems="center">
                <Switch>
                    <Route path={"/login"} component={LoginForm}/>
                    <Route path={"/signup"} component={Signup}/>
                </Switch>
                <ErrorMessage/>
            </Grid>
        </Container>
    );
}

export default MainView;
