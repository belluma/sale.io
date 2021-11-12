package capstone.backend.services;

import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.repo.OrderToSupplierRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static capstone.backend.utils.OrderToSupplierTestUtils.sampleOrder;
import static capstone.backend.utils.OrderToSupplierTestUtils.sampleOrderDTO;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class OrderToSupplierServiceTest {

    private final OrderToSupplierRepo orderRepo = mock(OrderToSupplierRepo.class);
    private final  ProductService productService = mock(ProductService.class);
    private final OrderItemService qtyService = mock(OrderItemService.class);
    private final OrderToSupplierService orderService = new OrderToSupplierService(orderRepo, productService, qtyService);


    @Test
    void getAllOrders() {
        //GIVEN
        when(orderRepo.findAll()).thenReturn(List.of(sampleOrder()));
        List<OrderToSupplierDTO> expected = List.of(sampleOrderDTO());
        //WHEN
        List<OrderToSupplierDTO> actual = orderService.getAllOrders();
        //THEN
        assertIterableEquals(actual, expected);
        verify(orderRepo).findAll();
    }

    @Test
    void createOrder() {
        //GIVEN
        OrderToSupplier orderToSave = sampleOrder();
        OrderToSupplierDTO expected = sampleOrderDTO();
        Long productId = orderToSave.getOrderItems().get(0).getProduct().getId();
        when(orderRepo.save(orderToSave)).thenReturn(orderToSave);
        when(orderRepo.findById(orderToSave.getId())).thenReturn(Optional.empty());
        when(productService.checkIfProductExists(productId)).thenReturn(true);
        //WHEN
        OrderToSupplierDTO actual = orderService.createOrder(sampleOrderDTO());
        //THEN
        verify(orderRepo).save(orderToSave);
        verify(orderRepo).findById(123L);
        verify(productService).checkIfProductExists(productId);
//        verify(qtyService).addOrderQuantity(qty);
        assertThat(actual, is(expected));
    }
}
