import React, {ChangeEvent} from 'react'
import { selectSuppliers} from "../../../slicer/supplierSlice";
import {mapCategoryToSelectData, mapSupplierToSelectData} from "../helper";
import {handleProductFormInput, selectProductToSave} from "../../../slicer/productSlice";
import {selectCategories} from "../../../slicer/categorySlice";

//component imports
import {Grid} from "@mui/material";
import CustomSelect from "../_elements/custom-select/CustomSelect";
import CustomText from "../_elements/custom-text/CustomText";

import CustomNumber from "../_elements/custom-number/CustomNumber";
//interface imports
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import NewCategory from "./new-category/NewCategory";


function ProductForm() {
    const dispatch = useAppDispatch();
    const productToSave = useAppSelector(selectProductToSave);
    const {name, purchasePrice, retailPrice, minAmount, maxAmount, unitSize, category} = productToSave;
    const supplierId = productToSave.suppliers?.length ? productToSave.suppliers[0].id : undefined;
    const categoryId = category?.id?.toString();
    const suppliers = useAppSelector(selectSuppliers);
    const categories = useAppSelector(selectCategories);
    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        dispatch(handleProductFormInput({...productToSave, [e.target.name]: value}));
    };
    const handleSupplierChange = (e: ChangeEvent<HTMLInputElement>) => {
        const selectedSupplier = suppliers.filter(s => s.id === e.target.value);
        dispatch(handleProductFormInput({...productToSave, suppliers: selectedSupplier}))
    }
    const handleCategoryChange = (e: ChangeEvent<HTMLInputElement>) => {
        const category = categories.find(c => c.id === +e.target.value)
        dispatch(handleProductFormInput({...productToSave, category}))
    }
    const [popperAnchor, setPopperAnchor] = React.useState<null | HTMLElement>(null);
    const popperOpen = Boolean(popperAnchor);
    const popperId = popperOpen ? 'new-category-popper' : undefined;
    const props = {onChange: handleChange, model: "product"};
    const supplierOptions = mapSupplierToSelectData(suppliers);
    const categoryOptions = mapCategoryToSelectData(categories)
    return (
        !suppliers.length ? <div>"Please create a supplier first</div> :
            <div>
                <Grid container rowSpacing={2}>
                    <Grid item xs={12}>
                        <CustomText name="name" label={"name"} required value={name} {...props} />
                    </Grid>
                    <Grid item xs={6}>
                        <CustomSelect name="supplier" label={"supplier"} value={supplierId} options={supplierOptions}
                                      onChange={handleSupplierChange} model={"supplier"} required/>
                    </Grid>
                    <Grid item xs={6}>
                        <CustomSelect name="category" label={"category"} value={categoryId}
                                      options={categoryOptions} onChange={handleCategoryChange} model={"category"} />
                    </Grid>
                    <NewCategory id={popperId} open={popperOpen} anchorEl={popperAnchor}/>
                    <Grid item xs={6}>
                        <CustomNumber currency name="purchasePrice" label={"purchase price"} value={purchasePrice} {...props} required/>
                    </Grid>
                    <Grid item xs={6}>
                        <CustomNumber currency name="retailPrice" label={"retail price"} value={retailPrice} {...props} />
                    </Grid>
                    <Grid item xs={6}>
                        <CustomNumber name="minAmount" label={"min amount"} value={minAmount} {...props} />
                    </Grid>
                    <Grid item xs={6}>
                        <CustomNumber name="maxAmount" label={"max amount"} value={maxAmount} {...props} />
                    </Grid>
                    <Grid item xs={6}>
                        <CustomNumber name={"unitSize"} label={"Unit size"} value={unitSize} {...props} required/>
                    </Grid>
                </Grid>
            </div>
    )
}

export default ProductForm;
