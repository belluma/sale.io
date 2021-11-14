import React from 'react'
import {selectOrderToSave} from "../../../../slicer/orderSlice";
import {useAppSelector} from "../../../../app/hooks";
import OrderItem from "../order-item/OrderItem";

//component imports

//interface imports

type Props = {};

function Preview(props: Props){
    const order = useAppSelector(selectOrderToSave);
    const {items, supplier} = order;
    const list = items.map(({product, quantity}, i) => {
        const total = (!product?.purchasePrice || !quantity) ? 0 : product.purchasePrice * quantity
        return<OrderItem key={i} productName={product?.name} quantity={quantity}
                   total={total}/>
    })
    return(
       <div>
           {list}</div>
    )
}

export default Preview;
