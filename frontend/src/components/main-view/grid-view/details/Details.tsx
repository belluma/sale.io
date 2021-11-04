import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {hideDetails, selectDetailsData, selectShowDetails} from "../../../../slicer/detailsSlice";
import {images} from '../helpers'

//component imports
import {Card, CardContent, CardHeader, CardMedia, Dialog, DialogContent, DialogProps, Divider} from "@mui/material";
import Login from "../../../security/login/Login";

//interface imports

type Props = {};


function Details(props: Props) {
    const [scroll] = React.useState<DialogProps['scroll']>('paper');
    const dispatch = useAppDispatch();
    const showDetails = useAppSelector(selectShowDetails);
    const detailsData = useAppSelector(selectDetailsData);
    const {title, subtitle, picture, alt, model} = detailsData;

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
    const cardContent = {
        none: (<div/>),
        login: <Login/>,
        employees: (<div/>),
        products: (<div/>),
        customers: (<div/>),
        suppliers: (<div/>),

    }
    return (
        <Dialog open={showDetails} onClose={handleClose}>
            <DialogContent dividers={scroll === 'paper'}>
                <Card sx={{width: 400, justifyContent: "center"}} ref={descriptionElementRef}>
                    <CardHeader title={title} subtitle={subtitle} align="center"/>
                    <Divider/>
                    <CardMedia
                        component="img"
                        sx={{width: 400}}
                        image={picture || images[model]}
                        alt={alt}
                    />
                    <CardContent>
                        {cardContent[model]}
                    </CardContent>
                </Card>
            </DialogContent>
        </Dialog>
    )
}

export default Details;
