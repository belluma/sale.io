package capstone.backend.utils;

import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderToSupplierDTO;

import java.util.List;

import static capstone.backend.model.enums.OrderStatus.PENDING;
import static capstone.backend.model.enums.OrderStatus.RECEIVED;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItem;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItemDTO;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplierDTO;

public class OrderToSupplierTestUtils {

    public static OrderToSupplier sampleOrderPending() {
        return OrderToSupplier
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItem()))
                .supplier(sampleSupplier())
                .status(PENDING)
                .build();
    }

    public static OrderToSupplier sampleOrderReceived() {
        return OrderToSupplier
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItem()))
                .supplier(sampleSupplier())
                .status(RECEIVED)
                .build();
    }

    public static OrderToSupplierDTO sampleOrderDTO() {
        return OrderToSupplierDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .supplier(sampleSupplierDTO())
                .build();
    }

    public static OrderToSupplierDTO sampleOrderDTOWithStatusPending() {
        return OrderToSupplierDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .supplier(sampleSupplierDTO())
                .status(PENDING)
                .build();
    }

    public static OrderToSupplierDTO sampleOrderDTOWithStatusReceived() {
        return OrderToSupplierDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .supplier(sampleSupplierDTO())
                .status(RECEIVED)
                .build();
    }

}
