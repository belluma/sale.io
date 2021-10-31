import React, {useEffect, useState } from 'react'
import { useHistory } from 'react-router';
import { useAppDispatch, useAppSelector } from '../../app/hooks';

//component imports
import { AppBar, IconButton, Slide, Toolbar, Typography, useScrollTrigger } from '@mui/material';
import LogoutIcon from '@mui/icons-material/Logout';
//interface imports

type Props = {
    children?: React.ReactElement;
};

export default Header;

function Header(props: Props){
    const history = useHistory();
    const dispatch = useAppDispatch();
    const [scrollTrigger, setScrollTrigger] = useState<boolean>(window.innerWidth < 900);

    const handleResize = () => {
        if (window.innerWidth < 900 && !scrollTrigger) setScrollTrigger(true)
        if (window.innerWidth >= 900 && scrollTrigger) setScrollTrigger(false)
    }
    useEffect(() => {
        window.addEventListener("resize", handleResize, false);
    })
    function HideOnScroll(props: Props) {
        const {children} = props;
        const trigger = useScrollTrigger({});
        return (
            <Slide appear={false} direction="down" in={!scrollTrigger ? true : !trigger}>
                {children}
            </Slide>
        );
    }
    return (
        <HideOnScroll {...props}>
            <AppBar sx={{bgcolor: 'primary.light'}}>
                <Toolbar sx={{justifyContent: "space-between"}}>
                    <Typography>AppName</Typography>
                </Toolbar>
                <Toolbar sx={{mb: 1, alignItems: "stretch", justifyContent: "space-between"}}>
                    <IconButton onClick={() => console.log('logout')} edge="end">
                        <LogoutIcon/>
                    </IconButton>
                </Toolbar>
            </AppBar>
        </HideOnScroll>
    )
}



