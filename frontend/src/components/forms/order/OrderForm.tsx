import React, {ChangeEvent} from 'react'
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {addProductToOrder, chooseSupplier, selectOrderToSave} from "../../../slicer/orderSlice";
import { selectProducts} from "../../../slicer/productSlice";
import {mapProductsToSelectData, mapSupplierToSelectData} from "../helper";
import {selectSuppliers} from "../../../slicer/supplierSlice";
import {productsBySupplier} from "./helper";
import {useOrders} from "./useOrder";
//component imports
import CustomSelect from "../_elements/custom-select/CustomSelect";
import CustomNumber from "../_elements/custom-number/CustomNumber";

import {Button, Grid} from "@mui/material";
import OrderPreview from "./preview/OrderPreview";

//interface imports

type Props = {};

function OrderForm(props: Props) {
    const dispatch = useAppDispatch();
    const orderToSave = useAppSelector(selectOrderToSave);
    const products = useAppSelector(selectProducts);
    const suppliers = useAppSelector(selectSuppliers);
    const {
        productToAdd,
        setProductToAdd,
        selectedProductId,
        setSelectedProductId,
        selectedSupplierId,
        setSelectedSupplierId,
        quantity,
        setQuantity
    }= useOrders(orderToSave, products)
    const productOptions = mapProductsToSelectData(products.filter(productsBySupplier, selectedSupplierId));
    const supplierOptions = mapSupplierToSelectData(suppliers);
    const selectProduct = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedProductId(e.target.value);
    }
    const selectSupplier = (e: ChangeEvent<HTMLInputElement>) => {
        const supplier = suppliers.find(s => s.id === e.target.value);
        supplier && dispatch(chooseSupplier(supplier));
        supplier && setSelectedSupplierId(supplier.id);
    }
    const changeQuantity = (e: ChangeEvent<HTMLInputElement>) => {
        setQuantity(+e.target.value);
    }
    const validateProduct = productToAdd?.product && productToAdd?.quantity;
    const addProduct = () => {
        //@ts-ignore check in line above
        if (validateProduct) dispatch(addProductToOrder(productToAdd));
        setProductToAdd({});
        setQuantity(0);
    }
    const showPreview = selectedSupplierId && orderToSave.orderItems?.length
    return (
        <Grid container>
            <Grid item xs={12}>
                <CustomSelect label={'supplier'} value={selectedSupplierId} name={"supplier"} options={supplierOptions}
                              onChange={selectSupplier} model="supplier" required
                              disabled={orderToSave.orderItems.length > 0}/>
            </Grid>
            <Grid item xs={12}>
                <h2>Add items to your order</h2>
            </Grid>
            <Grid item xs={8}>
                <CustomSelect label={'product'} value={selectedProductId} name="product" options={productOptions}
                              onChange={selectProduct} model="product" required disabled={!orderToSave.supplier}/>
            </Grid>
            <Grid item xs={2}>
                <CustomNumber label={'quantity'} value={quantity} name="quantity" onChange={changeQuantity} required/>
            </Grid>
            <Grid item xs={2}>
                <Button disabled={!validateProduct} onClick={addProduct}>Add</Button>
            </Grid>
            {showPreview ? <OrderPreview isFormEnabled={true}/> : <></>}
        </Grid>
    )
}

export default OrderForm;
