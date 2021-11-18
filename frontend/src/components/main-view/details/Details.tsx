import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {hideDetails, resetDetails, selectShowDetails} from "../../../slicer/detailsSlice";

//component imports
import {
    Container,
    Dialog,
    DialogContent, DialogContentText,
    DialogProps, Fab,
    IconButton,
    useMediaQuery,
    useTheme
} from "@mui/material";
import ClearIcon from '@mui/icons-material/Clear'
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
        <Dialog fullScreen={fullScreen} open={showDetails} onClose={handleClose} transitionDuration={transitionDuration} scroll='paper'>
            <DialogContent dividers sx={{padding:0, display:'flex', alignItems:alignItems, height:{md:800}}}>

                <Container ref={descriptionElementRef} sx={{margin:"auto"}}>
                    <Fab variant='circular' size='small' color="primary" sx={{position:"absolute", top:15, right:25}} onClick={handleClose}>
                        <ClearIcon />
                    </Fab>
                    <DetailsCard fullScreen={fullScreen} handleClose={handleClose}/>
                </Container>
            </DialogContent>
        </Dialog>
    )
}

export default Details;
