import React from 'react'
import {views} from "./helpers";


//component imports
import Signup from "./grid-view/details/security/signup/Signup";
import {Container, Grid} from "@mui/material";
import {Route, Switch} from "react-router";
import LoginView from "./views/login-view/LoginView";
import StartRoute from "./start-route/StartRoute";
import StartView from "./views/start-view/StartView";
import ProtectedRoute from "./protected-route/ProtectedRoute";
import EmployeesView from "./views/employees/EmployeesView";
import ProductsView from "./views/products/ProductsView";
import SuppliersView from "./views/suppliers/SuppliersView";
import CustomersView from "./views/customers/CustomersView";
import OrdersView from "./views/orders/OrdersView";
import Pending from "../messages/pending/Pending";
//interface imports
import {Model} from "../../interfaces/IThumbnailData";
import SuccessMessage from "../messages/success-message/SuccessMessage";

type Props = {};

function MainView(props: Props){
    const protectedViews = {
        NEW:StartView,
        LOGIN: StartView,
        EMPLOYEE: EmployeesView,
        PRODUCT: ProductsView,
        SUPPLIER: SuppliersView,
        CUSTOMER: CustomersView,
        ORDER: OrdersView
    };
    const protectedRoutes = views.map((view) => <ProtectedRoute key={view} path={`/${Model[view]}`} component={protectedViews[view]} />)
    return (
        <Container sx={{pt: 15}} maxWidth={false}>
            <Pending />
            <SuccessMessage />
            <Grid container justifyContent="center" alignItems="center">
                <Switch>
                    <Route path={"/login"} component={LoginView}/>
                    <Route path={"/signup"} component={Signup}/>
                    <Route path={"/start"} component={StartView}/>
                    <Route path={"/"} exact component={StartRoute} />
                    {protectedRoutes}
                </Switch>
            </Grid>
        </Container>
    );
}

export default MainView;
