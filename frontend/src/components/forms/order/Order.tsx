import React, {ChangeEvent, useEffect, useState} from 'react'
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {addProductToOrder, getAllOrders, handleOrderFormInput, selectCurrentOrder, selectOrderToSave} from "../../../slicer/orderSlice";
import CustomSelect from "../_elements/custom-select/CustomSelect";
import {Button, Grid, Toolbar} from "@mui/material";
import {getAllProducts, selectProducts} from "../../../slicer/productSlice";
import {mapProductsToSelectData} from "../helper";
import CustomNumber from "../_elements/custom-number/CustomNumber";
import {IOrderItem} from "../../../interfaces/IOrder";
import OrderItem from "./order-item/OrderItem";

//component imports

//interface imports

type Props = {};

function Order(props: Props) {
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllProducts());
        dispatch(getAllOrders());
    }, []);
    const orderToSave = useAppSelector(selectOrderToSave);
    const currentOrder = useAppSelector(selectCurrentOrder);
    const [productToAdd, setProductToAdd] = useState<IOrderItem>();
    const [selectedProductId, setSelectedProductId] = useState<string>();
    const [quantity, setQuantity] = useState<number>(0);
    const products = useAppSelector(selectProducts);
    const productOptions = mapProductsToSelectData(products);
    useEffect(() => {
        let product;
        if (selectedProductId) {
            product = products.filter(p => p.id === selectedProductId)[0];
        }
        setProductToAdd({product, quantity})
    }, [selectedProductId, quantity])
    const selectProduct = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedProductId(e.target.value);
    }
    const changeQuantity = (e: ChangeEvent<HTMLInputElement>) => {
        setQuantity(+e.target.value);
    }
    const validateProduct = productToAdd?.product && productToAdd?.quantity;
    const addProduct = () => {
        //@ts-ignore check in line above
        if (validateProduct) dispatch(addProductToOrder(productToAdd));
        setProductToAdd({});
        setSelectedProductId(undefined);
        setQuantity(0);
    }
    const addOrderToList = ({product, quantity}:IOrderItem, index:number) => {
        if(!product || !quantity) return <></>;
        //@ts-ignore check in line above
        return <OrderItem key={index} productName={product.name} quantity={quantity} total={product.purchasePrice * quantity} />
    }
    const orderItems = orderToSave ? orderToSave.items.map(addOrderToList) : <></>;
    return (
        <div>
            <h2>Add items to your order</h2>
            <Grid container>
                <Grid item xs={8}>
                    <CustomSelect label={'product'} value={selectedProductId} name="product" options={productOptions}
                                  handleChange={selectProduct}
                                  model="product"/>
                </Grid>
                <Grid item xs={2}>
                    <CustomNumber label={'quantity'} value={quantity} name="quantity" handleChange={changeQuantity}
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
