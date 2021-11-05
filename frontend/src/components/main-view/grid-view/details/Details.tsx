import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {hideDetails, selectShowDetails} from "../../../../slicer/detailsSlice";

//component imports
import {Card, CardContent, CardHeader, CardMedia, Dialog, DialogContent, DialogProps, Divider} from "@mui/material";
import Login from "../../../security/login/Login";
import ExistingCard from "./existing-card/ExistingCard";

//interface imports

type Props = {};


function Details(props: Props) {
    const [scroll] = React.useState<DialogProps['scroll']>('paper');
    const dispatch = useAppDispatch();
    const showDetails = useAppSelector(selectShowDetails);
    const descriptionElementRef = React.useRef<HTMLDivElement>(null);
    React.useEffect(() => {
        if (showDetails) {
            const {current: descriptionElement} = descriptionElementRef;
            if (descriptionElement !== null) {
                descriptionElement.focus();
            }
        }
    }, [showDetails]);
    const handleClose = () => {
        dispatch(hideDetails());
    }

    return (
        <Dialog open={showDetails} onClose={handleClose}>
            <DialogContent dividers={scroll === 'paper'}>
                <div ref={descriptionElementRef}>
                    <ExistingCard/>
                </div>

            </DialogContent>
        </Dialog>
    )
}

export default Details;
