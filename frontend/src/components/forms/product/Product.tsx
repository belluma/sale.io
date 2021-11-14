import React, {ChangeEvent, useEffect} from 'react'

//component imports
import {Grid, Toolbar} from "@mui/material";
import CustomSelect from "../_elements/custom-select/CustomSelect";
import CustomText from "../_elements/custom-text/CustomText";
import CustomNumber from "../_elements/custom-number/CustomNumber";

//interface imports
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {getAllSuppliers, selectSuppliers} from "../../../slicer/supplierSlice";
import {mapSupplierToSelectData} from "../helper";
import {handleProductFormInput, selectProductToSave} from "../../../slicer/productSlice";


function Product() {
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllSuppliers())
    }, [dispatch]);
    const productToSave = useAppSelector(selectProductToSave);
    const suppliers = useAppSelector(selectSuppliers);
    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        dispatch(handleProductFormInput({...productToSave, [e.target.name]: value}));
    };
    const handleSupplierChange = (e: ChangeEvent<HTMLInputElement>) => {
        const selectedSupplier = suppliers.filter(s => s.id === e.target.value);
        dispatch(handleProductFormInput({...productToSave, suppliers: selectedSupplier}))
    }
    const props = {onChange: handleChange, model: "product"};
    const supplierOptions = mapSupplierToSelectData(suppliers);
    return (
        !suppliers.length ? <div>"Please create a supplier first</div> :
            <div>
                <Grid container>
                    <Grid item xs={12}>
                        <CustomText name="name" label={"name"}  {...props} />
                    </Grid>
                    <Grid item xs={6}>
                        <CustomSelect name="supplier" label={"supplier"} options={supplierOptions}
                                      onChange={handleSupplierChange} model={"product"}/>
                    </Grid>
                    <Grid item xs={6}><CustomSelect name="category" label={"category"}
                                                    options={[{id: '1', name: `vegetables`}]} {...props}  />
                    </Grid>
                    <Grid item xs={6}>
                        <CustomNumber currency name="purchasePrice" label={"purchase price"}  {...props} />
                    </Grid>
                    <Grid item xs={6}><CustomNumber currency name="retailPrice" label={"retail price"}  {...props} />
                    </Grid>
                    <Grid item xs={6}>
                        <CustomNumber name="minAmount" label={"min amount"}  {...props} />
                    </Grid>
                    <Grid item xs={6}><CustomNumber name="maxAmount" label={"max amount"}  {...props} />
                    </Grid>
                    <Grid item xs={6}>
                        <CustomNumber name={"unitSize"} label={"Unit size"} {...props} />
                    </Grid>
                </Grid>
            </div>
    )
}

export default Product;
