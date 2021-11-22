import React, {useEffect, useState } from 'react'
//component imports
import { AppBar, Slide, Toolbar, Typography, useScrollTrigger } from '@mui/material';
import HeaderButtons from "./header-buttons/HeaderButtons";
import {selectShowDetails} from "../../slicer/detailsSlice";
import {useAppSelector} from "../../app/hooks";

//interface imports

type Props = {
    //typescript complaining children can't be undefined since last update
    children?:any
};
export const appBarHeight = 120;
export default Header;

function Header(props: Props){
    const showDetails = useAppSelector(selectShowDetails);
    const [scrollTrigger, setScrollTrigger] = useState<boolean>(window.innerWidth < 900);
    const handleResize = () => {
        if (window.innerWidth < 900 && !scrollTrigger) setScrollTrigger(true)
        if (window.innerWidth >= 900 && scrollTrigger) setScrollTrigger(false)
    }
    useEffect(() => {
        window.addEventListener("resize", handleResize, false);
    })
    function HideOnScroll({children}: Props) {
        const trigger = useScrollTrigger({});
        const show = showDetails ? false : !trigger;
        return (
            <Slide appear={false} direction="down" in={!scrollTrigger ? true : show}>
                {children}
            </Slide>
        );
    }


    return (
        <HideOnScroll {...props}>
            <AppBar sx={{bgcolor: 'primary.light' ,height:appBarHeight, zIndex:1400}}>
                <Toolbar sx={{justifyContent: "space-between"}}>
                    <Typography>Saleio</Typography>
                </Toolbar>
                <HeaderButtons appBarHeight={appBarHeight} />
            </AppBar>
         </HideOnScroll>
    )
}




