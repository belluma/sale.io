import React from 'react'
import {views} from "./helpers";


//component imports
import Signup from "../security/signup/Signup";
import {Container, Grid} from "@mui/material";
import {Route, Switch} from "react-router";
import ErrorMessage from "../error-message/ErrorMessage";
import LoginView from "./views/login-view/LoginView";
import StartRoute from "./start-route/StartRoute";
import StartView from "./views/start-view/StartView";
import ProtectedRoute from "./protected-route/ProtectedRoute";
import Employees from "./views/employees/Employees";
import Products from "./views/products/Products";
import Suppliers from "./views/suppliers/Suppliers";
import Customers from "./views/customers/Customers";
//interface imports
import {Views} from "../../interfaces/IThumbnailData";

type Props = {};

function MainView(props: Props){
    const protectedViews = {
        NEW:StartView,
        LOGIN: StartView,
        EMPLOYEE: Employees,
        PRODUCT: Products,
        SUPPLIER: Suppliers,
        CUSTOMER: Customers,
    };
    const protectedRoutes = views.map((view) => <ProtectedRoute key={view} path={`/${Views[view]}`} component={protectedViews[view]} />)
    return (
        <Container sx={{pt: 15}} maxWidth={false}>
            <Grid container justifyContent="center" alignItems="center">
                <Switch>
                    <Route path={"/login"} component={LoginView}/>
                    <Route path={"/signup"} component={Signup}/>
                    <Route path={"/start"} component={StartView}/>
                    <Route path={"/"} exact component={StartRoute} />
                    {protectedRoutes}
                </Switch>
                <ErrorMessage/>
            </Grid>
        </Container>
    );
}

export default MainView;
