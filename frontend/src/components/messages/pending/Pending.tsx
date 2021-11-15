import React from 'react'
import {useAppSelector} from "../../../app/hooks";
import {selectOrderPending} from "../../../slicer/orderSlice";
import {selectSupplierPending} from "../../../slicer/supplierSlice";
import {selectEmployeePending} from "../../../slicer/employeeSlice";
import {selectProductPending} from "../../../slicer/productSlice";
//component imports

import {CircularProgress, Dialog, DialogContent} from "@mui/material";

//interface imports

type Props = {};

function Pending(props: Props) {
    const product = useAppSelector(selectProductPending);
    const employee = useAppSelector(selectEmployeePending);
    const order = useAppSelector(selectOrderPending);
    const supplier = useAppSelector(selectSupplierPending);
    const hide = [product, employee, order, supplier].some(x => !!x) ? "" : "is-hidden";
    const style = {
        position: "absolute",
        left: "50%",
        top: "50%",
        transform: "translateX(-50%) translateY(-50%)"
    } as const
    return (
        <div style={style} className={hide}>
            <CircularProgress sx={{background: "transparent"}} size={250}/>
        </div>
    )
}

export default Pending;
