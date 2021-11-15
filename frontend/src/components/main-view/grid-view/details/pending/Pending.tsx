import React from 'react'
import {useAppSelector} from "../../../../../app/hooks";
import {selectOrderPending} from "../../../../../slicer/orderSlice";
import {selectSupplierPending} from "../../../../../slicer/supplierSlice";
import {selectEmployeePending} from "../../../../../slicer/employeeSlice";
import {selectProductPending} from "../../../../../slicer/productSlice";

//component imports

//interface imports

type Props = {};

function Pending(props: Props){
    const product = useAppSelector(selectProductPending);
    const employee = useAppSelector(selectEmployeePending);
    const order = useAppSelector(selectOrderPending);
    const supplier = useAppSelector(selectSupplierPending);
    console.log(product, employee, order, supplier)
    const hide = [product, employee, order, supplier].every(x => !x) ? "is-hidden" : "";
    return(
       <div className={hide}>Pending</div>
    )
}

export default Pending;
