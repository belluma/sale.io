package capstone.backend.utils;

import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderToSupplierDTO;

import java.util.List;

import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItem;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItemDTO;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplierDTO;

public class OrderToSupplierTestUtils {

    public static OrderToSupplier sampleOrder(){
        return OrderToSupplier
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItem()))
                .supplier(sampleSupplier())
                .build();
    }
public static OrderToSupplierDTO sampleOrderDTO(){
        return OrderToSupplierDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .supplier(sampleSupplierDTO())
                .build();
    }

}
