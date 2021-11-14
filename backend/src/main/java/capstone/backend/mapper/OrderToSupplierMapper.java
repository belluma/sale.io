package capstone.backend.mapper;

import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderToSupplierDTO;

import static capstone.backend.mapper.SupplierMapper.mapSupplier;

public class OrderToSupplierMapper {

    private OrderToSupplierMapper() {

    }

    public static OrderToSupplier mapOrder(OrderToSupplierDTO order) {
        OrderToSupplier mappedOrder = OrderToSupplier
                .builder()
                .supplier(mapSupplier(order.getSupplier()))
                .orderItems(order.getOrderItems())
                .build();
        if (order.getId() != null) {
            mappedOrder.setId(order.getId());
        }
        return mappedOrder;
    }


    public static OrderToSupplierDTO mapOrder(OrderToSupplier order) {
        OrderToSupplierDTO mappedOrder = OrderToSupplierDTO
                .builder()
                .supplier(mapSupplier(order.getSupplier()))
                .orderItems(order.getOrderItems())
                .build();
        if (order.getId() != null) {
            mappedOrder.setId(order.getId());
        }
        return mappedOrder;
    }
}
