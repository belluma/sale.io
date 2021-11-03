import React from 'react'
import {Card, CardContent, CardHeader, ClickAwayListener, Dialog, Divider, Typography} from "@mui/material";
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {hideDetails, selectShowDetails} from "../../../../slicer/detailsSlice";

//component imports

//interface imports

type Props = {};

function Details(props: Props){
    const dispatch = useAppDispatch();
    const showDetails = useAppSelector(selectShowDetails);
    const handleClose = () => {
        dispatch(hideDetails());
    }
    return(
        <Dialog open={showDetails} onClose={handleClose} >
                <Card>
                    <CardHeader title="Error!!1!" align="center"/>
                    <Divider/>
                    <CardContent >
                        <Typography variant="h2">details</Typography>
                    </CardContent>
                </Card>
        </Dialog>
    )
}

export default Details;
