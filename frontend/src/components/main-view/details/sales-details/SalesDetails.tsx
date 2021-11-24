import React, {useState} from 'react'
import {useAppSelector} from "../../../../app/hooks";
import {selectCurrentProduct} from "../../../../slicer/productSlice";

//component imports

import {Button, Fab, Grid, IconButton, Typography} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import {getSubTotal, getTotal} from "../../../forms/order/helper";
import { selectCurrentCustomer } from '../../../../slicer/customerSlice';

//interface imports

type Props = {};

function SalesDetails(props: Props) {
    const product = useAppSelector(selectCurrentProduct);
    const order = useAppSelector(selectCurrentCustomer);
    const [quantity, setQuantity] = useState(0);
    const increase = () => {
        product.amountInStock && quantity < product.amountInStock && setQuantity(quantity + 1);
    }
    const decrease = () => {
        quantity > 0 && setQuantity(quantity - 1);
    }
    const subTotal = getSubTotal({product, quantity});
    const total = order.orderItems.reduce(getTotal, 0);
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
                <Grid item >
                    <Fab color={"primary"} onClick={increase}>
                        <AddIcon/>
                    </Fab>
                </Grid>
            </Grid>
            <Grid item alignSelf={'flex-end'}>
                <Typography variant={'body2'} component={'p'}>Price per unit: €{product.retailPrice?.toFixed(2)}</Typography>
                <Typography variant={'body2'} component={'p'}>already on bill: €{total?.toFixed(2)}</Typography>
                <Typography variant={'body2'} component={'p'}>to be aded: €{subTotal?.toFixed(2)}</Typography>
                <Typography variant={'body2'} component={'p'}>new total: €{total || 0 + subTotal?.toFixed(2)}</Typography>
            </Grid>

        </Grid>
    )
}

export default SalesDetails;
