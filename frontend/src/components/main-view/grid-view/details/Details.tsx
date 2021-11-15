import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {hideDetails, resetDetails, selectShowDetails} from "../../../../slicer/detailsSlice";

//component imports
import {Dialog, DialogContent, DialogProps, useMediaQuery, useTheme} from "@mui/material";
import DetailsCard from "./details-card/DetailsCard";
import Pending from "./pending/Pending";

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
    const handleClose = () => {
        dispatch(resetDetails());
        dispatch(hideDetails());
    }

    return (
        <Dialog fullScreen={fullScreen} open={showDetails} onClose={handleClose} >
            <DialogContent dividers={scroll === 'paper'} sx={{padding:0, display:'flex', alignItems:alignItems}}>
                <div ref={descriptionElementRef} style={{margin:"auto"}}>
                    <DetailsCard fullScreen={fullScreen} handleClose={handleClose}/>
                </div>
            </DialogContent>
        </Dialog>
    )
}

export default Details;
