package capstone.backend.utils;

import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.order.OrderToCustomerDTO;

import java.util.List;

import static capstone.backend.model.enums.OrderStatus.OPEN;
import static capstone.backend.model.enums.OrderStatus.PAID;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItem;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItemDTO;

public class OrderToCustomerTestUtils {
    public static OrderToCustomer emptyOrderOpen() {
        return OrderToCustomer
                .builder()
                .id(123L)
                .orderItems(List.of())
                .status(OPEN)
                .build();
    }

    public static OrderToCustomer emptyOrderPaid() {
        return OrderToCustomer
                .builder()
                .id(123L)
                .orderItems(List.of())
                .status(PAID)
                .build();
    }

    public static OrderToCustomerDTO emptyOrderDTO() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of())
                .build();
    }

    public static OrderToCustomerDTO emptyOrderDTOWithStatusOpen() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of())
                .status(OPEN)
                .build();
    }

    public static OrderToCustomerDTO emptyOrderDTOWithStatusPaid() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of())
                .status(PAID)
                .build();
    }
 public static OrderToCustomer orderOpenWithOrderItem() {
        return OrderToCustomer
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItem()))
                .status(OPEN)
                .build();
    }

    public static OrderToCustomer OrderPaidWithOrderItem() {
        return OrderToCustomer
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItem()))
                .status(PAID)
                .build();
    }

    public static OrderToCustomerDTO OrderDTOWithOrderItem() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .build();
    }

    public static OrderToCustomerDTO OrderDTOWithStatusOpenWithOrderItem() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .status(OPEN)
                .build();
    }

    public static OrderToCustomerDTO OrderDTOWithStatusPaidWithOrderItem() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .status(PAID)
                .build();
    }
}
