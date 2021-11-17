import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {selectProductSuccess, closeSuccess as closeProductSuccess} from "../../../slicer/productSlice";
import {selectEmployeeSuccess, closeSuccess as closeEmployeeSuccess} from "../../../slicer/employeeSlice";
import {selectOrderSuccess, closeSuccess as closeOrderSuccess} from "../../../slicer/orderSlice";
import {selectSupplierSuccess, closeSuccess as closeSupplierSuccess} from "../../../slicer/supplierSlice";
import {green} from "@mui/material/colors";
//component imports
import {
    Avatar,
    Button,
    Card,
    CardActions,
    CardContent,
    CardHeader,
    Dialog,
    DialogContent,
    Typography
} from "@mui/material";
import CheckIcon from '@mui/icons-material/Check'
//interface imports

type Props = {};

function SuccessMessage(props: Props) {
    const product = useAppSelector(selectProductSuccess);
    const employee = useAppSelector(selectEmployeeSuccess);
    const order = useAppSelector(selectOrderSuccess);
    const supplier = useAppSelector(selectSupplierSuccess);
    const dispatch = useAppDispatch();
    const close = () => {
        dispatch(closeProductSuccess());
        dispatch(closeEmployeeSuccess());
        dispatch(closeOrderSuccess());
        dispatch(closeSupplierSuccess());
}
    const open = [product, employee, order, supplier].some(x => !!x);

       return (
        <Dialog open={open} sx={{bgcolor:'transparent', padding:0}} PaperProps={{sx:{boxShadow:"none", bgcolor:"transparent"}}}>
            <DialogContent>
        <Card sx={{width:0.99, padding:1}} >
            <CardHeader align='center' sx={{display:"grid",justifyContent:"center"}} avatar={<Avatar sx={{ bgcolor: green[500]}}><CheckIcon/></Avatar>}/>
            <CardContent sx={{textAlign:'center'}}>
                <Typography variant="h5">
                    Operation Successful
                </Typography>
                </CardContent>
            <CardActions>
                <Button fullWidth variant="contained" color={"success"} onClick={close}>OK</Button>
            </CardActions>
        </Card>
            </DialogContent>
        </Dialog>

    )
}

export default SuccessMessage;
