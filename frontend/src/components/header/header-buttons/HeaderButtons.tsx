import React, {useState} from 'react'

import {useHistory} from "react-router";
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {views} from "../../main-view/helpers";
import {logout, selectLoggedIn} from "../../../slicer/authSlice";
import {hideDetails} from "../../../slicer/detailsSlice";
import {buttonStyles, useIconButtonStyles} from "./styles";
//component imports
import {Button, IconButton, Toolbar, useMediaQuery, useTheme} from "@mui/material";
import LogoutIcon from "@mui/icons-material/Logout";
import MenuIcon from "@mui/icons-material/Menu";
import Drawer from "../../drawer/Drawer";
import ChangeView from "./change-view/ChangeView";
//interface imports
import {Views} from "../../../interfaces/IThumbnail";

type Props = {
    appBarHeight: number,
};

function HeaderButtons({appBarHeight}: Props) {
    const theme = useTheme();
    const smallScreen = useMediaQuery(theme.breakpoints.down('sm'));
    const [drawerOpen, setDrawerOpen] = useState(false);
    const toggleDrawer = () => setDrawerOpen(!drawerOpen);
    const history = useHistory()
    const dispatch = useAppDispatch();
    const loggedIn = useAppSelector(selectLoggedIn);
    const handleLogout = () => dispatch(logout());
    const reroute = (e: React.MouseEvent<HTMLButtonElement>) => {
        //@ts-ignore check happens on buttons
        history.push(Views[e.currentTarget.name])
        dispatch(hideDetails());
        setDrawerOpen(false);
    }
    const buttons = views.map((view) => {
        return <Button fullWidth sx={buttonStyles} disabled={!loggedIn}
                       variant={'contained'} size={'small'} key={view} name={view} onClick={reroute}>{view}S</Button>
    });
    const burgerMenu = <IconButton onClick={toggleDrawer}><MenuIcon/></IconButton>
    const iconClasses = useIconButtonStyles();

    return (
        <div>
            <Drawer open={drawerOpen} toggle={toggleDrawer} buttons={buttons}
                    marginTop={appBarHeight}/>
            <Toolbar sx={{mb: 1, alignItems: "center", justifyContent: "space-between"}}>
                {smallScreen ? burgerMenu : buttons}
                {!smallScreen && <ChangeView key={"changeView"}/>}
                {loggedIn && <IconButton classes={{root: iconClasses.goldFont}} onClick={handleLogout} edge="end">
                    <LogoutIcon/>
                </IconButton>}
            </Toolbar>
        </div>

    )
}

export default HeaderButtons;
