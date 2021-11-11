package capstone.backend.utils;

import capstone.backend.model.db.order.OrderQuantity;

import static capstone.backend.utils.OrderToSupplierTestUtils.sampleOrder;
import static capstone.backend.utils.ProductTestUtils.sampleProduct;

public class OrderQuantityUtils {

    public static OrderQuantity sampleOrderQuantity(){
        return OrderQuantity
                .builder()
                .product(sampleProduct())
                .order(sampleOrder())
                .quantity(23)
                .build();
    }
}
