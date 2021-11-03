import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {hideDetails, selectDetailsData, selectShowDetails} from "../../../../slicer/detailsSlice";

//component imports
import {Card, CardContent, CardHeader, Dialog, Divider, Typography} from "@mui/material";

//interface imports

type Props = {
};

function Details(props: Props){

    const dispatch = useAppDispatch();
    const showDetails = useAppSelector(selectShowDetails);
    const detailsData = useAppSelector(selectDetailsData);
    const {title, subtitle, picture, id, alt} = detailsData;

    const handleClose = () => {
        dispatch(hideDetails());
    }
    return(
        <Dialog open={showDetails} onClose={handleClose} >
                <Card>
                    <CardHeader title={title} subtitle={subtitle} align="center"/>
                    <Divider/>
                    <CardContent >
                        <Typography variant="h2">details</Typography>
                    </CardContent>
                </Card>
        </Dialog>
    )
}

export default Details;
