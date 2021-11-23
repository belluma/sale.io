import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {
    hideDetails,
    resetDetails,
    resetGoBack,
    selectGoBack,
    selectShowDetails,
    setDetailData
} from "../../../slicer/detailsSlice";
import { selectCurrentSupplier } from '../../../slicer/supplierSlice';
import {parseSupplierToThumbnail} from "../../../interfaces/IThumbnail";
//component imports
import {
    Container,
    Dialog,
    DialogContent,
     Fab,
    useMediaQuery,
    useTheme
} from "@mui/material";
import ClearIcon from '@mui/icons-material/Clear'
import ArrowBackIcon from '@mui/icons-material/ArrowBack'
import DetailsCard from "./details-card/DetailsCard";


//interface imports

type Props = {};


function Details(props: Props) {
    const dispatch = useAppDispatch();
    const showDetails = useAppSelector(selectShowDetails);
    const supplier = useAppSelector(selectCurrentSupplier);
    const goBack = useAppSelector(selectGoBack);
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
    const backToSupplier = () => {
        dispatch(resetGoBack());
        dispatch(setDetailData(parseSupplierToThumbnail(supplier)));
    }

    return (
        <Dialog fullScreen={fullScreen} open={showDetails} onClose={handleClose} transitionDuration={transitionDuration} scroll='paper'>
            <DialogContent dividers sx={{padding:0, display:'flex', alignItems:alignItems, height:{md:800}}}>
                <Container ref={descriptionElementRef} sx={{margin:"auto", height: 0.99}}>
                    {goBack && <Fab variant='circular' size='small' color="primary" sx={{position:"absolute", top:15, left:25}} onClick={backToSupplier}>
                        <ArrowBackIcon />
                    </Fab>}
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
