import React from 'react'

import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {logout, selectLoggedIn} from "../../../slicer/authSlice";

//component imports
import {IconButton, Toolbar} from "@mui/material";
import LogoutIcon from "@mui/icons-material/Logout";
//interface imports

type Props = {};

function HeaderButtons(props: Props) {
    const dispatch = useAppDispatch();
    const loggedIn = useAppSelector(selectLoggedIn);
    const handleLogout = () => dispatch(logout());
    return (
        <Toolbar sx={{mb: 1, alignItems: "stretch", justifyContent: "space-between"}}>
            {loggedIn && <IconButton onClick={handleLogout} edge="end">
                <LogoutIcon/>
            </IconButton>}
        </Toolbar>

    )
}

export default HeaderButtons;
