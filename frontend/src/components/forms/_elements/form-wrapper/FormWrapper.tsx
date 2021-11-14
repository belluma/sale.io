import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {
    createSupplier,
    selectSupplierToSave,
    validateSupplier
} from "../../../../slicer/supplierSlice";
import {toBeReplaced} from "../../../../slicer/employeeSlice";
import {createProduct, selectProductToSave, validateProduct} from "../../../../slicer/productSlice";
import {createOrder, selectOrderToSave, validateOrder} from "../../../../slicer/orderSlice";
//component imports
import {Button} from "@mui/material";
import Employee from "../../employee/Employee";
import Customer from "../../customer/Customer";
import Supplier from "../../supplier/Supplier";
import Product from "../../product/Product";
import Order from "../../order/Order";

//interface imports
import {Model} from "../../../../interfaces/IThumbnailData";


type Props = {
    model: Model,
    fullScreen: boolean,
    handleClose: () => void
};

function FormWrapper({model, fullScreen, handleClose}: Props) {
    const dispatch = useAppDispatch();
    const product = useAppSelector(selectProductToSave)
    const supplier = useAppSelector(selectSupplierToSave)
    const order = useAppSelector(selectOrderToSave)

    const disableButton = () => {
        switch(model){
            case Model.EMPLOYEE:
                break;
            case Model.CUSTOMER:
                break;
            case Model.PRODUCT:
                return !validateProduct(product);
            case Model.SUPPLIER:
                return !validateSupplier(supplier);
            case Model.ORDER:
                return !validateOrder(order);
            default:
                return false;
        }
    };
    const formSelector = {
        none: "couldn't find the right form",
        employee: <Employee/>,
        product: <Product/>,
        customer: <Customer/>,
        supplier: <Supplier/>,
        order: <Order/>,
    };
    const submitSelector = {
        employee: toBeReplaced,
        product: createProduct,
        customer: toBeReplaced,
        supplier: createSupplier,
        order: createOrder,
    };
    const handleSubmit = () => {
        dispatch(submitSelector[model]());
    }
    return (
        <form>{formSelector[model]}
            <section>
                <Button onClick={handleSubmit} disabled={disableButton()}>save</Button>
                {fullScreen && <Button onClick={handleClose}>Close</Button>}
            </section>
        </form>
    )
}

export default FormWrapper;
