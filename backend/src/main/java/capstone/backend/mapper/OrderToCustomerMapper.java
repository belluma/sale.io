package capstone.backend.mapper;

import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.order.OrderToCustomerDTO;

import static capstone.backend.model.enums.OrderStatus.PENDING;

public class OrderToCustomerMapper {

    private OrderToCustomerMapper(){
    }

    public static OrderToCustomer mapOrder(OrderToCustomerDTO order){
        OrderToCustomer mappedOrder = OrderToCustomer
                .builder()
                .orderItems(order
                        .getOrderItems()
                        .stream()
                        .map(OrderItemMapper::mapOrderItem)
                        .toList())
                .build();
        if (order.getId() != null) {
            mappedOrder.setId(order.getId());
        }
        if (order.getStatus() == null){
            mappedOrder.setStatus(PENDING);
        }else {
            mappedOrder.setStatus(order.getStatus());
        }
        return mappedOrder;
    }
public static OrderToCustomerDTO mapOrder(OrderToCustomer order){
        OrderToCustomerDTO mappedOrder = OrderToCustomerDTO
                .builder()
                .orderItems(order
                        .getOrderItems()
                        .stream()
                        .map(OrderItemMapper::mapOrderItem)
                        .toList())
                .build();
        if (order.getId() != null) {
            mappedOrder.setId(order.getId());
        }
        if (order.getStatus() == null){
            mappedOrder.setStatus(PENDING);
        }else {
            mappedOrder.setStatus(order.getStatus());
        }
        return mappedOrder;
    }
}
