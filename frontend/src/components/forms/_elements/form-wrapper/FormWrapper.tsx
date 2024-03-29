import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {
    createSupplier,
    selectSupplierToSave,
    validateSupplier
} from "../../../../slicer/supplierSlice";
import {saveEmployee, selectEmployeeToSave, toBeReplaced, validateEmployee} from "../../../../slicer/employeeSlice";
import {createProduct, selectProductToSave, validateProduct} from "../../../../slicer/productSlice";
import {createOrder, selectOrderToSave, validateOrder} from "../../../../slicer/orderSlice";
//component imports
import {Button, Container} from "@mui/material";
import EmployeeForm from "../../employee/EmployeeForm";
import Customer from "../../customer/Customer";
import SupplierForm from "../../supplier/SupplierForm";
import ProductForm from "../../product/ProductForm";
import OrderForm from "../../order/OrderForm";

//interface imports
import {Model} from "../../../../interfaces/IThumbnail";


type Props = {
    model: Model,
    fullScreen: boolean,
    handleClose: () => void
};

function FormWrapper({model, handleClose}: Props) {
    const dispatch = useAppDispatch();
    const product = useAppSelector(selectProductToSave)
    const supplier = useAppSelector(selectSupplierToSave)
    const order = useAppSelector(selectOrderToSave)
    const employee = useAppSelector(selectEmployeeToSave)

    const disableButton = () => {
        switch (model) {
            case Model.EMPLOYEE:
                return !validateEmployee(employee);
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
        employee: <EmployeeForm/>,
        product: <ProductForm/>,
        customer: <Customer/>,
        supplier: <SupplierForm/>,
        order: <OrderForm/>,
    };

    const submitSelector = {
        customer: toBeReplaced,
        product: createProduct,
        employee: saveEmployee,
        supplier: createSupplier,
        order: createOrder,
    };
    const handleSubmit = () => {
        console.log(model)
        if (Object.keys(submitSelector).includes(model)) dispatch(submitSelector[model]());
        handleClose();
    }
    return (
        <Container sx={{display: "flex", flexDirection: "column", height: 0.99}}>
            <Container sx={{flexGrow: 1}}>

                {formSelector[model]}
            </Container>
            <section>
                <Button variant='contained' sx={{mt:1}} onClick={handleSubmit} disabled={disableButton()}>save</Button>
            </section>
        </Container>
    )
}

export default FormWrapper;
