package capstone.backend.utils;

import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderToSupplierDTO;

import java.util.List;

import static capstone.backend.utils.OrderItemTestUtils.sampleOrderQuantity;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplierDTO;

public class OrderToSupplierTestUtils {

    public static OrderToSupplier sampleOrder(){
        return OrderToSupplier
                .builder()
                .id(123L)
                .orderQuantity(List.of(sampleOrderQuantity()))
                .supplier(sampleSupplier())
                .build();
    }
public static OrderToSupplierDTO sampleOrderDTO(){
        return OrderToSupplierDTO
                .builder()
                .id(123L)
                .orderQuantity(List.of(sampleOrderQuantity()))
                .supplier(sampleSupplierDTO())
                .build();
    }

}
