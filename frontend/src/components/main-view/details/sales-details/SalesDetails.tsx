import React, {useState} from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {selectCurrentProduct} from "../../../../slicer/productSlice";

//component imports

import {Button, Fab, Grid, Typography} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import BillPreview from "./bill-preview/BillPreview";
import {addItemsToOrder} from "../../../../slicer/customerSlice";

//interface imports

type Props = {};

function SalesDetails(props: Props) {
    const dispatch = useAppDispatch();
    const product = useAppSelector(selectCurrentProduct);
    const [quantity, setQuantity] = useState(0);
    const addItems = () => {
        console.log(quantity)
        quantity > 0 && dispatch(addItemsToOrder({product, quantity}));
    }
    const increase = () => {
        product.amountInStock && quantity < product.amountInStock && setQuantity(quantity + 1);
    }
    const decrease = () => {
        quantity > 0 && setQuantity(quantity - 1);
    }
    return (
        <Grid container flexDirection="column" alignItems={'center'} sx={{height: 0.99}}>
            <Grid item>
                <Typography variant="h5" component='h2' align="center">Add to bill</Typography>
            </Grid>
            <Grid item container flexDirection='row' sx={{flexGrow: 1}} alignItems='center' justifyContent='center'>
                <Grid item>
                    <Fab color={"primary"} onClick={decrease}>
                        <RemoveIcon/>
                    </Fab>
                </Grid>
                <Grid item xs={4}>
                    <Typography variant="h3" component='p' align="center">{quantity}</Typography>
                </Grid>
                <Grid item>
                    <Fab color={"primary"} onClick={increase}>
                        <AddIcon/>
                    </Fab>
                </Grid>
            </Grid>
            <BillPreview quantity={quantity}/>
            <Grid item alignSelf='flex-end'><Button variant='contained' onClick={addItems} disabled={!quantity}>Add</Button></Grid>
        </Grid>
    )
}

export default SalesDetails;
