import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {handleFormInput, saveItem, selectItemToSave} from "../../../../slicer/newItemSlice";

//component imports
import {Button} from "@mui/material";
import Employee from "../../employee/Employee";
import Customer from "../../customer/Customer";
import Supplier from "../../supplier/Supplier";
import Product from "../../product/Product";

//interface imports
import {Buttons} from "../../../../interfaces/IThumbnailData";
import {selectSuppliers} from "../../../../slicer/supplierSlice";

type Props = {
    model: Buttons,
    fullScreen:boolean,
    handleClose:()=> void
};

function FormWrapper({model, fullScreen, handleClose}: Props) {
    const dispatch = useAppDispatch();
    const itemToSave = useAppSelector(selectItemToSave)
    const suppliers = useAppSelector(selectSuppliers);
    const handleInput = (e: React.ChangeEvent<HTMLInputElement>) => {
        const updatedValue = {[e.target.name]: e.target.value}
        dispatch(handleFormInput({...itemToSave, ...updatedValue}))
    }
    const handleSubmit = () => {
        dispatch(saveItem(model))
    }
    const disableButton = () => {
        return (model === Buttons.PRODUCT && !suppliers.length)
            }
    const formSelector = {
        none: "couldn't find the right form",
        employee: <Employee/>,
        product: <Product handleChange={handleInput}/>,
        customer: <Customer/>,
        supplier: <Supplier handleChange={handleInput}/>,
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
