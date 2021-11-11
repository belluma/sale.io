package capstone.backend.utils;

import capstone.backend.model.db.order.OrderToSupplier;

public class OrderToSupplierTestUtils {

    public static OrderToSupplier sampleOrder(){
        return OrderToSupplier
                .builder()
                .build();
    }

}
