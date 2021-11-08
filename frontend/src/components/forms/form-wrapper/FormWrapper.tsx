import React from 'react'
import Employee from "../employee/Employee";
import Customer from "../customer/Customer";
import Supplier from "../supplier/Supplier";
import Product from "../product/Product";
import {Buttons} from "../../../interfaces/IThumbnailData";
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {handleFormInput, selectItemToSave} from "../../../slicer/newItemSlice";
import {Button} from "@mui/material";

//component imports

//interface imports

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
        console.log(itemToSave)
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
