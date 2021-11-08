import React from 'react'
import Employee from "../employee/Employee";
import Customer from "../customer/Customer";
import Supplier from "../supplier/Supplier";
import Product from "../product/Product";
import {Buttons} from "../../../interfaces/IThumbnailData";

//component imports

//interface imports

type Props = {
    model:Buttons
};

function FormWrapper({model}: Props){
    const formSelector = {
        none: "couldn't find the right form",
        employee: <Employee />,
        product: <Product />,
        customer: <Customer />,
        supplier: <Supplier />,
    }
console.log(formSelector[model])
    return(
       <div>{formSelector[model]}</div>
    )
}

export default FormWrapper;
