import React, {ChangeEvent, useEffect, useState} from 'react'
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {addProductToOrder, getAllOrders, selectOrderToSave} from "../../../slicer/orderSlice";
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

type Props = {};

function Order(props: Props) {
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllProducts());
        dispatch(getAllSuppliers());
        dispatch(getAllOrders());
    }, [dispatch]);
    const orderToSave = useAppSelector(selectOrderToSave);
    const [productToAdd, setProductToAdd] = useState<IOrderItem>();
    const [selectedProductId, setSelectedProductId] = useState<string>();
    const [selectedSupplierId, setSelectedSupplierId] = useState<string>();
    const [quantity, setQuantity] = useState<number>(0);
    const products = useAppSelector(selectProducts);
    const suppliers = useAppSelector(selectSuppliers);
    const productOptions = mapProductsToSelectData(products.filter(productsBySupplier, selectedSupplierId));
    const supplierOptions = mapSupplierToSelectData(suppliers);
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
    const addOrderToList = ({product, quantity}: IOrderItem, index: number) => {
        if (!product || !quantity) return;
        return <OrderItem key={index} productName={product.name} quantity={quantity}
            //@ts-ignore function returns before getting here in case either of the values is undefined
                          total={product.purchasePrice * quantity}/>
    }
    const orderItems = orderToSave ? orderToSave.items.map(addOrderToList) : <></>;
    return (
        <div>
            <Toolbar>
                <CustomSelect label={'supplier'} value={selectedSupplierId} name={"supplier"} options={supplierOptions}
                              onChange={selectSupplier} model="supplier"/>
            </Toolbar>
            <h2>Add items to your order</h2>
            <Grid container>
                <Grid item xs={8}>
                    <CustomSelect label={'product'} value={selectedProductId} name="product" options={productOptions}
                                  onChange={selectProduct}
                                  model="product"/>
                </Grid>
                <Grid item xs={2}>
                    <CustomNumber label={'quantity'} value={quantity} name="quantity" onChange={changeQuantity}
                                  model="order"/></Grid>
                <Grid item xs={2}>
                    <Button disabled={!validateProduct} onClick={addProduct}>Add</Button>
                </Grid>
            </Grid>
            {orderItems}
        </div>
    )
}

export default Order;
