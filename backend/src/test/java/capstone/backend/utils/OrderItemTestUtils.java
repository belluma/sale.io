package capstone.backend.utils;

import capstone.backend.model.db.order.OrderItem;

public class OrderQuantityTestUtils {

    public static OrderItem sampleOrderQuantity(){
        return OrderItem
                .builder()
                .productId(1L)
                .orderId(1L)
                .quantity(1)
                .build();
    }
}
