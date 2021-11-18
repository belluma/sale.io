package capstone.backend.utils;

import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.order.OrderToCustomerDTO;

import java.util.List;

import static capstone.backend.model.enums.OrderStatus.OPEN;
import static capstone.backend.model.enums.OrderStatus.PAID;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItem;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItemDTO;

public class OrderToCustomerTestUtils {
    public static OrderToCustomer sampleOrderOpen() {
        return OrderToCustomer
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItem()))
                .status(OPEN)
                .build();
    }

    public static OrderToCustomer sampleOrderPaid() {
        return OrderToCustomer
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItem()))
                .status(PAID)
                .build();
    }

    public static OrderToCustomerDTO sampleOrderDTO() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .build();
    }

    public static OrderToCustomerDTO sampleOrderDTOWithStatusOpen() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .status(OPEN)
                .build();
    }

    public static OrderToCustomerDTO sampleOrderDTOWithStatusPaid() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .status(PAID)
                .build();
    }
}
