import React from 'react'
import {useAppSelector} from "../../../app/hooks";
import {selectProductSuccess} from "../../../slicer/productSlice";
import {selectEmployeeSuccess} from "../../../slicer/employeeSlice";
import {selectOrderSuccess} from "../../../slicer/orderSlice";
import {selectSupplierSuccess} from "../../../slicer/supplierSlice";

//component imports

//interface imports

type Props = {};

function SuccessMessage(props: Props){
    const product = useAppSelector(selectProductSuccess);
    const employee = useAppSelector(selectEmployeeSuccess);
    const order = useAppSelector(selectOrderSuccess);
    const supplier = useAppSelector(selectSupplierSuccess);
    const hide = [product, employee, order, supplier].some(x => !!x) ? "" : "is-hidden";
    const style = {
        position: "absolute",
        left: "50%",
        top: "50%",
        transform: "translateX(-50%) translateY(-50%)"
    } as const
    return(
       <div style={style} className={hide} >SuccessMessage</div>
    )
}

export default SuccessMessage;
