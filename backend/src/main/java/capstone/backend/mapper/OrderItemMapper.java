package capstone.backend.mapper;


import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.dto.order.OrderItemInfo;
import capstone.backend.model.dto.order.OrderItemDTO;

import static capstone.backend.mapper.ProductMapper.*;

public class OrderItemMapper {

    private OrderItemMapper() {

    }


    public static OrderItem mapOrderItem(OrderItemDTO orderItem) {
        return OrderItem
                .builder()
                .id(orderItem.getId())
                .product(mapProduct(orderItem.getProduct()))
                .quantity(orderItem.getQuantity())
                .build();
    }

    public static OrderItemDTO mapOrderItem(OrderItem orderItem) {
        return OrderItemDTO
                .builder()
                .id(orderItem.getId())
                .product(mapProductWithDetails(orderItem.getProduct()))
                .quantity(orderItem.getQuantity())
                .build();
    }

    public static OrderItemInfo mapToOrderItemInfo(OrderItem orderItem) {
        return OrderItemInfo
                .builder()
                .id(orderItem.getId())
                .product(mapToProductInfo(orderItem.getProduct()))
                .quantity(orderItem.getQuantity())
                .build();
    }
}
