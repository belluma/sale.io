import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {hideDetails, resetDetails, selectShowDetails} from "../../../../slicer/detailsSlice";

//component imports
import {Container, Dialog, DialogContent, DialogProps, useMediaQuery, useTheme} from "@mui/material";
import DetailsCard from "./details-card/DetailsCard";

//interface imports

type Props = {};


function Details(props: Props) {
    const [scroll] = React.useState<DialogProps['scroll']>('paper');
    const dispatch = useAppDispatch();
    const showDetails = useAppSelector(selectShowDetails);
    const descriptionElementRef = React.useRef<HTMLDivElement>(null);
    const theme = useTheme();
    const fullScreen = useMediaQuery(theme.breakpoints.down('sm'));
    const alignItems = fullScreen ? "center" : "";
    React.useEffect(() => {
        if (showDetails) {
            const {current: descriptionElement} = descriptionElementRef;
            if (descriptionElement !== null) {
                descriptionElement.focus();
            }
        }
    }, [showDetails]);
    const transitionDuration = 200
    const handleClose = () => {
        setTimeout(() => {dispatch(resetDetails())}, transitionDuration);
        dispatch(hideDetails());
    }

    return (
        <Dialog fullScreen={fullScreen} open={showDetails} onClose={handleClose} transitionDuration={transitionDuration}>
            <DialogContent dividers={scroll === 'paper'} sx={{padding:0, display:'flex', alignItems:alignItems}}>
                <Container ref={descriptionElementRef} style={{margin:"auto"}}>
                    <DetailsCard fullScreen={fullScreen} handleClose={handleClose}/>
                </Container>
            </DialogContent>
        </Dialog>
    )
}

export default Details;
