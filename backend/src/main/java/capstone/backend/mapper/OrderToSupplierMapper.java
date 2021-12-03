package capstone.backend.mapper;

import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.model.dto.order.OrderToSupplierInfo;

import static capstone.backend.mapper.SupplierMapper.mapSupplier;
import static capstone.backend.model.enums.OrderToSupplierStatus.PENDING;

public class OrderToSupplierMapper {

    private OrderToSupplierMapper() {

    }

    public static OrderToSupplier mapOrder(OrderToSupplierDTO order) {
        return OrderToSupplier
                .builder()
                .id(order.getId() != null ? order.getId() : null)
                .supplier(mapSupplier(order.getSupplier()))
                .orderItems(order
                        .getOrderItems()
                        .stream()
                        .map(OrderItemMapper::mapOrderItem)
                        .toList())
                .status(order.getStatus() == null ? PENDING : order.getStatus())
                .build();
    }


    public static OrderToSupplierDTO mapOrder(OrderToSupplier order) {
        return OrderToSupplierDTO
                .builder()
                .id(order.getId() != null ? order.getId() : null)
                .supplier(mapSupplier(order.getSupplier()))
                .orderItems(order
                        .getOrderItems()
                        .stream()
                        .map(OrderItemMapper::mapOrderItem)
                        .toList())
                .status(order.getStatus())
                .build();
    }

    public static OrderToSupplierInfo mapToOrderInfo(OrderToSupplier order) {
       return  OrderToSupplierInfo
                .builder()
                .id(order.getId() != null ? order.getId() : null)
                .orderItems(order
                        .getOrderItems()
                        .stream()
                        .map(OrderItemMapper::mapOrderItem)
                        .toList())
                .status(order.getStatus() == null ? PENDING : order.getStatus())
                .build();
    }
}
