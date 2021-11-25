import React from 'react'
import {receiveOrder, removeOrderItem, selectCurrentOrder, selectOrderToSave} from "../../../../slicer/orderSlice";
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";

//component imports
import {
    Button,
    CardHeader,
    createTheme,
    Divider,
    ThemeOptions,
    ThemeProvider,
} from "@mui/material";

//interface imports
import {OrderStatus} from "../../../../interfaces/OrderStatus";
import {IOrder} from "../../../../interfaces/IOrder";
import {parseName} from "../../../main-view/thumbnail/helper";
import OrderDetails from "../../../main-view/details/order-details/OrderDetails";
import {dividerStyles} from "../../../main-view/details/styles";

type Props = {
    isFormEnabled?: boolean,
};

function OrderPreview({isFormEnabled}: Props) {
    const dispatch = useAppDispatch();
    const orderToSave = useAppSelector(selectOrderToSave)
    const currentOrder = useAppSelector(selectCurrentOrder);
    const order: IOrder = isFormEnabled ? orderToSave : currentOrder;
    const {orderItems, supplier} = order;
    const handleReceive = () => {
        if (order.status === OrderStatus.PENDING) dispatch(receiveOrder(order));
    }
    const greenButton = (): ThemeOptions => {
        return createTheme({
            palette: {
                primary: {
                    main: "#388e3c"
                },
            }
        })
    }
    const ReceiveButton = () =>  (<ThemeProvider theme={greenButton()}>
        {!isFormEnabled && order.status === OrderStatus.PENDING &&
        <Button onClick={handleReceive} variant="contained">Receive</Button>}
    </ThemeProvider>)

    return (
        <OrderDetails isFormEnabled={isFormEnabled} orderItems={orderItems} handleRemove={removeOrderItem}>
            {order.status === OrderStatus.PENDING ?
               <ReceiveButton />: <></>}
            <CardHeader title={`order to ${supplier && parseName(supplier)}`}/>
            <Divider sx={dividerStyles}/>
        </OrderDetails>
    )
}

export default OrderPreview;
