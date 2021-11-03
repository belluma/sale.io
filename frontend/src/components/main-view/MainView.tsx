import React from 'react'


//component imports
import Signup from "../security/signup/Signup";
import {Container, Grid} from "@mui/material";
import {Route, Switch} from "react-router";
import ErrorMessage from "../error-message/ErrorMessage";
import LoginView from "./views/login-view/LoginView";
import StartRoute from "./start-route/StartRoute";
import StartView from "./views/start-view/StartView";
//interface imports

type Props = {};

function MainView(props: Props){
    return (
        <Container sx={{pt: 15}} maxWidth={false}>
            <Grid container justifyContent="center" alignItems="center">
                <Switch>
                    <Route path={"/login"} component={LoginView}/>
                    <Route path={"/signup"} component={Signup}/>
                    <Route path={"/start"} component={StartView}/>
                    <Route path={"/"} component={StartRoute} />
                </Switch>
                <ErrorMessage/>
            </Grid>
        </Container>
    );
}

export default MainView;
