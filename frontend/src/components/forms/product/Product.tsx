import React from 'react'
import {TextField, Toolbar} from "@mui/material";
import CustomSelect from "../custom-select/CustomSelect";
import {Buttons} from "../../../interfaces/IThumbnailData";
import CustomText from "../custom-text/CustomText";
import CustomNumber from "../custom-number/CustomNumber";

//component imports

//interface imports

type Props = {};

function Product(props: Props) {
    const handleChange = () => {}
    return (
        <div>
           <CustomText label={"name"} handleChange={handleChange} model={"product"} />
            <CustomSelect label={"supplier"} options={[]} model={"product"}  handleChange={handleChange}/>
            <CustomSelect label={"category"} options={[]} model={"product"}  handleChange={handleChange}/>
            <Toolbar>
                <CustomNumber label={"purchase price"} handleChange={handleChange} model={"model"} />
                <CustomNumber label={"retail price"} handleChange={handleChange} model={"model"} />
            </Toolbar>
            <Toolbar>
                <CustomNumber label={"min amount"} handleChange={handleChange} model={"model"} />
                <CustomNumber label={"max amount"} handleChange={handleChange} model={"model"} />
            </Toolbar>
        </div>
    )
}

export default Product;
