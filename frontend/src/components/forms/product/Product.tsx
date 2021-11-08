import React from 'react'
import {TextField, Toolbar} from "@mui/material";
import CustomSelect from "../custom-select/CustomSelect";
import CustomText from "../custom-text/CustomText";
import CustomNumber from "../custom-number/CustomNumber";
import {IFormProps} from "../../../interfaces/INewItem";

//component imports

//interface imports

type Props = IFormProps

function Product({handleChange}: Props) {
    const props = {handleChange: handleChange, model: "product"}
    return (
        <div>
            <CustomText name="name" label={"name"}  {...props} />
            <CustomSelect name="supplier" label={"supplier"} options={[]} {...props}  />
            <CustomSelect name="category" label={"category"} options={[]} {...props}  />
            <Toolbar>
                <CustomNumber name="purchasePrice" label={"purchase price"}  {...props} />
                <CustomNumber name="retailPrice" label={"retail price"}  {...props} />
            </Toolbar>
            <Toolbar>
                <CustomNumber name="minAmount" label={"min amount"}  {...props} />
                <CustomNumber name="maxAmount" label={"max amount"}  {...props} />
            </Toolbar>
        </div>
    )
}

export default Product;
