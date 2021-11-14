import {useEffect, useState} from "react";
import {IOrder, IOrderItem} from "../../../interfaces/IOrder";
import {IProduct} from "../../../interfaces/IProduct";

export const useOrders = (orderToSave: IOrder, products: IProduct[]) => {
    const [productToAdd, setProductToAdd] = useState<IOrderItem>();
    const [selectedProductId, setSelectedProductId] = useState<string>();
    const [selectedSupplierId, setSelectedSupplierId] = useState<string>();
    const [quantity, setQuantity] = useState<number>(0);

    useEffect(() => {
        const {supplier} = orderToSave
        if (supplier?.id) setSelectedSupplierId(supplier.id)
    }, [orderToSave]);

    useEffect(() => {
        let product;
        if (selectedProductId) {
            product = products.filter(p => p.id === selectedProductId)[0];
        }
        setProductToAdd({product, quantity})
    }, [selectedProductId, quantity, products])

    return {
        productToAdd,
        setProductToAdd,
        selectedProductId,
        setSelectedProductId,
        selectedSupplierId,
        setSelectedSupplierId,
        quantity,
        setQuantity
    }
}
