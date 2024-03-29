import React from 'react'
import {views} from "./helpers";


//component imports
import Signup from "./details/security/signup/Signup";
import {Container} from "@mui/material";
import {Route, Switch, useLocation} from "react-router";
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
import {Model} from "../../interfaces/IThumbnail";
import SuccessMessage from "../messages/success-message/SuccessMessage";
import SalesViewCategories from "./views/sales-view/SalesViewCategories";
import SalesViewProducts from "./views/sales-view/SalesViewProducts";

type Props = {};

function MainView(props: Props) {
    const protectedViews = {
        NEW: StartView,
        LOGIN: StartView,
        EMPLOYEE: EmployeesView,
        PRODUCT: ProductsView,
        SUPPLIER: SuppliersView,
        CUSTOMER: CustomersView,
        ORDER: OrdersView
    };
    const isStart = useLocation().pathname.includes("start");
    const paddingTop = isStart ? `55px` : 15;
    const protectedRoutes = views.map((view) => <ProtectedRoute key={view} path={`/${Model[view]}`}
                                                                component={protectedViews[view]}/>)
    return (
        <Container sx={{pt: paddingTop, minHeight: `100vh`, bgcolor: "primary.lighter"}} maxWidth={false}
                   disableGutters={isStart}>
            <Pending/>
            <SuccessMessage/>
            <Switch>
                <Route path={"/login"} component={LoginView}/>
                <Route path={"/signup"} component={Signup}/>
                <Route path={"/start"} component={StartView}/>
                <Route path={"/sales"}  component={SalesViewCategories}/>
                <Route path={"/category"} component={SalesViewProducts}/>
                <Route path={"/"} exact component={StartRoute}/>
                {protectedRoutes}
            </Switch>
        </Container>
    );
}

export default MainView;
