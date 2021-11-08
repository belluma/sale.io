import React from 'react'

//component imports
import { Toolbar} from "@mui/material";
import CustomSelect from "../custom-select/CustomSelect";
import CustomText from "../custom-text/CustomText";
import CustomNumber from "../custom-number/CustomNumber";

//interface imports

import {IFormProps} from "../../../interfaces/INewItem";

type Props = IFormProps

function Product({handleChange}: Props) {
    const props = {handleChange: handleChange, model: "product"}
    return (
        <div>
            <CustomText name="name" label={"name"}  {...props} />
            <CustomSelect name="supplier" label={"supplier"} options={[`farmer's market`]} {...props}  />
            <CustomSelect name="category" label={"category"} options={[`vegetables`]} {...props}  />
            <Toolbar>
                <CustomNumber currency name="purchasePrice" label={"purchase price"}  {...props} />
                <CustomNumber currency name="retailPrice" label={"retail price"}  {...props} />
            </Toolbar>
            <Toolbar>
                <CustomNumber name="minAmount" label={"min amount"}  {...props} />
                <CustomNumber name="maxAmount" label={"max amount"}  {...props} />
            </Toolbar>
        </div>
    )
}

export default Product;
