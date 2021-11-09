import React, {useEffect} from 'react'

//component imports
import {Toolbar} from "@mui/material";
import CustomSelect from "../_elements/custom-select/CustomSelect";
import CustomText from "../_elements/custom-text/CustomText";
import CustomNumber from "../_elements/custom-number/CustomNumber";

//interface imports
import {IFormProps} from "../../../interfaces/INewItem";
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {getAllSuppliers, selectSuppliers} from "../../../slicer/supplierSlice";
import {mapSupplierToSelectData} from "../helper";


type Props = IFormProps

function Product({handleChange}: Props) {
    const props = {handleChange: handleChange, model: "product"}
    const dispatch = useAppDispatch();
    useEffect(() => {dispatch(getAllSuppliers())},[dispatch])
    const suppliers = mapSupplierToSelectData(useAppSelector(selectSuppliers));

    return (
        !suppliers.length ? <div>"Please create a supplier first</div> :
            <div>
                <CustomText name="name" label={"name"}  {...props} />
                <CustomSelect name="supplier" label={"supplier"} options={suppliers} {...props}  />
                <CustomSelect name="category" label={"category"} options={[{id:'1', name:`vegetables`}]} {...props}  />
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
