import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {handleFormInput, saveItem, selectItemToSave} from "../../../slicer/newItemSlice";

//component imports
import {Button} from "@mui/material";
import Employee from "../employee/Employee";
import Customer from "../customer/Customer";
import Supplier from "../supplier/Supplier";
import Product from "../product/Product";

//interface imports
import {Buttons} from "../../../interfaces/IThumbnailData";

type Props = {
    model: Buttons
};

function FormWrapper({model}: Props) {
    const dispatch = useAppDispatch();
    const itemToSave = useAppSelector(selectItemToSave)
    const handleInput = (e: React.ChangeEvent<HTMLInputElement>) => {
        const updatedValue = {[e.target.name]: e.target.value}
        dispatch(handleFormInput({...itemToSave, ...updatedValue}))
    }
    const handleSubmit = () => {
        console.log(123)
        dispatch(saveItem("product"))
    }
    const formSelector = {
        none: "couldn't find the right form",
        employee: <Employee/>,
        product: <Product handleChange={handleInput}/>,
        customer: <Customer/>,
        supplier: <Supplier/>,
    }
    return (
        <form>{formSelector[model]}
            <section>
                <Button onClick={handleSubmit}>save</Button>
            </section>
        </form>
    )
}

export default FormWrapper;
