import React from 'react'

import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {logout, selectLoggedIn} from "../../../slicer/authSlice";

//component imports
import {Button, IconButton, Toolbar, useMediaQuery, useTheme} from "@mui/material";
import LogoutIcon from "@mui/icons-material/Logout";
import MenuIcon from "@mui/icons-material/Menu";
//interface imports
import {Views} from "../../../interfaces/IThumbnailData";
import {useHistory} from "react-router";
import {views} from "../../main-view/helpers";

type Props = {};

function HeaderButtons(props: Props) {
    const theme = useTheme();
    const smallScreen = useMediaQuery(theme.breakpoints.down('sm'));
    const history = useHistory()
    //@ts-ignore
    const reroute = (e:React.MouseEvent<HTMLButtonElement>) => {history.push(Views[e.currentTarget.name])}
    const dispatch = useAppDispatch();
    const loggedIn = useAppSelector(selectLoggedIn);
    const handleLogout = () => dispatch(logout());
    const buttons =views.map((view) => <Button key={view} name={view} onClick={reroute}>{view}S</Button>)
    return (
        <Toolbar sx={{mb: 1, alignItems: "stretch", justifyContent: "space-between"}}>
            {smallScreen ? <IconButton>
                <MenuIcon/>
            </IconButton> :buttons}
            {loggedIn && <IconButton onClick={handleLogout} edge="end">
                <LogoutIcon/>
            </IconButton>}
        </Toolbar>

    )
}

export default HeaderButtons;
