import React from 'react'
import {useAppSelector} from "../../../../../app/hooks";
import {selectOrderPending} from "../../../../../slicer/orderSlice";
import {selectSupplierPending} from "../../../../../slicer/supplierSlice";
import {selectEmployeePending} from "../../../../../slicer/employeeSlice";
import {selectProductPending} from "../../../../../slicer/productSlice";
//component imports

import {CircularProgress, Dialog, DialogContent} from "@mui/material";

//interface imports

type Props = {};

function Pending(props: Props){
    const product = useAppSelector(selectProductPending);
    const employee = useAppSelector(selectEmployeePending);
    const order = useAppSelector(selectOrderPending);
    const supplier = useAppSelector(selectSupplierPending);
    const open = [product, employee, order, supplier].some(x => !!x)
    return(
        <Dialog  open={open}  >
            <DialogContent>
              <CircularProgress />
            </DialogContent>
        </Dialog>
    )
}

export default Pending;
