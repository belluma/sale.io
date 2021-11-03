import React from 'react'
import {Card, CardContent, CardHeader, Dialog, Divider, Typography} from "@mui/material";
import {useAppSelector} from "../../../../app/hooks";
import {selectShowDetails} from "../../../../slicer/detailsSlice";

//component imports

//interface imports

type Props = {};

function Details(props: Props){
    const showDetails = useAppSelector(selectShowDetails)
    return(
        <Dialog open={showDetails} >
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
