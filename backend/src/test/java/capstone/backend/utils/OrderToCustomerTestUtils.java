package capstone.backend.utils;

import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.model.dto.order.OrderToCustomerDTO;

import java.util.List;

import static capstone.backend.model.enums.OrderToCustomerStatus.OPEN;
import static capstone.backend.model.enums.OrderToCustomerStatus.PAID;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItem;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItemDTO;
import static capstone.backend.utils.ProductTestUtils.sampleProductDTOWithDetailsWithId;

public class OrderToCustomerTestUtils {
    public static OrderToCustomer emptyOrderOpen() {
        return OrderToCustomer
                .builder()
                .id(123L)
                .orderItems(List.of())
                .status(OPEN)
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

 public static OrderToCustomer orderOpenWithOrderItem() {
        return OrderToCustomer
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItem()))
                .status(OPEN)
                .build();
    }

    public static OrderToCustomer orderPaidWithOrderItem() {
        return OrderToCustomer
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItem()))
                .status(PAID)
                .build();
    }

    public static OrderToCustomerDTO orderDTOWithStatusOpenWithOrderItem() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .status(OPEN)
                .build();
    }

    public static OrderToCustomerDTO orderDTOWithStatusPaidWithOrderItem() {
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of(sampleOrderItemDTO()))
                .status(PAID)
                .build();
    }

    public static OrderToCustomerDTO orderDTOWithThreeItemsAndStatusOpen(){
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of(new OrderItemDTO(1L, sampleProductDTOWithDetailsWithId().withId(1L), 1),
                        sampleOrderItemDTO(),
                        new OrderItemDTO(3L, sampleProductDTOWithDetailsWithId().withId(3L), 1)))
                .status(OPEN)
                .build();
    }
public static OrderToCustomerDTO orderDTOWithTwoItemsAndStatusOpen(){
        return OrderToCustomerDTO
                .builder()
                .id(123L)
                .orderItems(List.of(new OrderItemDTO(1L, sampleProductDTOWithDetailsWithId().withId(1L), 1),
                        new OrderItemDTO(3L, sampleProductDTOWithDetailsWithId().withId(3L), 1)))
                .status(OPEN)
                .build();
    }
}
