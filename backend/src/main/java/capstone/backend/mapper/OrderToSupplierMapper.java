package capstone.backend.mapper;

import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderToSupplierDTO;

import static capstone.backend.mapper.SupplierMapper.mapSupplier;
import static capstone.backend.model.enums.OrderToSupplierStatus.PENDING;

public class OrderToSupplierMapper {

    private OrderToSupplierMapper() {

    }

    public static OrderToSupplier mapOrder(OrderToSupplierDTO order) {
        OrderToSupplier mappedOrder = OrderToSupplier
                .builder()
                .supplier(mapSupplier(order.getSupplier()))
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


    public static OrderToSupplierDTO mapOrder(OrderToSupplier order) {
        OrderToSupplierDTO mappedOrder = OrderToSupplierDTO
                .builder()
                .supplier(mapSupplier(order.getSupplier()))
                .orderItems(order
                        .getOrderItems()
                        .stream()
                        .map(OrderItemMapper::mapOrderItem)
                        .toList())
                .status(order.getStatus())
                .build();
        if (order.getId() != null) {
            mappedOrder.setId(order.getId());
        }
        return mappedOrder;
    }
}
