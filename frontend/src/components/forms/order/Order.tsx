import React, {ChangeEvent, useEffect, useState} from 'react'
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {addProductToOrder, chooseSupplier, getAllOrders, selectOrderToSave} from "../../../slicer/orderSlice";
import {getAllProducts, selectProducts} from "../../../slicer/productSlice";
import {mapProductsToSelectData, mapSupplierToSelectData} from "../helper";

//component imports
import CustomSelect from "../_elements/custom-select/CustomSelect";
import OrderItem from "./order-item/OrderItem";
import CustomNumber from "../_elements/custom-number/CustomNumber";

//interface imports
import {IOrderItem} from "../../../interfaces/IOrder";
import {Button, Grid, Toolbar} from "@mui/material";
import {getAllSuppliers, selectSuppliers} from "../../../slicer/supplierSlice";
import {productsBySupplier} from "./helper";
import Preview from "./preview/Preview";

type Props = {};

function Order(props: Props) {
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllProducts());
        dispatch(getAllSuppliers());
        dispatch(getAllOrders());
    }, [dispatch]);
    const orderToSave = useAppSelector(selectOrderToSave);
    const products = useAppSelector(selectProducts);
    const suppliers = useAppSelector(selectSuppliers);
    const [productToAdd, setProductToAdd] = useState<IOrderItem>();
    const [selectedProductId, setSelectedProductId] = useState<string>();
    const [selectedSupplierId, setSelectedSupplierId] = useState<string>();
    const [quantity, setQuantity] = useState<number>(0);
    const productOptions = mapProductsToSelectData(products.filter(productsBySupplier, selectedSupplierId));
    const supplierOptions = mapSupplierToSelectData(suppliers);
    useEffect(() => {
        const {supplier} = orderToSave
        if (supplier?.id) setSelectedSupplierId(supplier.id)
    }, []);

    useEffect(() => {
        let product;
        if (selectedProductId) {
            product = products.filter(p => p.id === selectedProductId)[0];
        }
        setProductToAdd({product, quantity})
    }, [selectedProductId, quantity, products])
    const selectProduct = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedProductId(e.target.value);
    }
    const selectSupplier = (e: ChangeEvent<HTMLInputElement>) => {
        const supplier = suppliers.find(s => s.id === e.target.value);
        supplier && dispatch(chooseSupplier(supplier));
        setSelectedSupplierId(e.target.value);
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
    return (
        <Grid container>
            <Grid item xs={12}>
                <CustomSelect label={'supplier'} value={selectedSupplierId} name={"supplier"} options={supplierOptions}
                              onChange={selectSupplier} model="supplier" required
                              disabled={orderToSave.items.length > 0}/>
            </Grid>
            <Grid item xs={12}>
                <h2>Add items to your order</h2>
            </Grid>
            <Grid item xs={8}>
                <CustomSelect label={'product'} value={selectedProductId} name="product" options={productOptions}
                              onChange={selectProduct} model="product" required disabled={!orderToSave.supplier}/>
            </Grid>
            <Grid item xs={2}>
                <CustomNumber label={'quantity'} value={quantity} name="quantity" onChange={changeQuantity}
                              model="order" required/>
            </Grid>
            <Grid item xs={2}>
                <Button disabled={!validateProduct} onClick={addProduct}>Add</Button>
            </Grid>
            {selectedSupplierId && <Preview/>}
        </Grid>
    )
}

export default Order;
