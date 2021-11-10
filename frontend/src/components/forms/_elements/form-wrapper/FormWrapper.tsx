import React from 'react'
import { useAppSelector} from "../../../../app/hooks";

//component imports
import {Button} from "@mui/material";
import Employee from "../../employee/Employee";
import Customer from "../../customer/Customer";
import Supplier from "../../supplier/Supplier";
import Product from "../../product/Product";

//interface imports
import {Model} from "../../../../interfaces/IThumbnailData";
import {selectSuppliers} from "../../../../slicer/supplierSlice";

type Props = {
    model: Model,
    fullScreen:boolean,
    handleClose:()=> void
};

function FormWrapper({model, fullScreen, handleClose}: Props) {
    const suppliers = useAppSelector(selectSuppliers);

    const handleSubmit = () => {
    }
    const disableButton = () => {
        return (model === Model.PRODUCT && !suppliers.length)
            }
    const formSelector = {
        none: "couldn't find the right form",
        employee: <Employee/>,
        product: <Product />,
        customer: <Customer/>,
        supplier: <Supplier />,
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
