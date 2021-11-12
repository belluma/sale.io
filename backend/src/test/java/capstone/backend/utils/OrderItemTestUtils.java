package capstone.backend.utils;

import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.dto.order.OrderItemDTO;

import static capstone.backend.utils.ProductTestUtils.sampleProductDTOWithDetailsWithId;
import static capstone.backend.utils.ProductTestUtils.sampleProductWithId;

public class OrderItemTestUtils {

    public static OrderItem sampleOrderItem(){
        return OrderItem
                .builder()
                .id(1L)
                .product(sampleProductWithId())
                .quantity(1)
                .build();
    }

    public static OrderItemDTO sampleOrderItemDTO() {}{
        return OrderItemDTO
                .builder()
                .id(1L)
                .product(sampleProductDTOWithDetailsWithId())
                .quantity(1)
                .build();
    }
}
