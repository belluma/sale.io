package capstone.backend.services;

import capstone.backend.exception.model.EntityNotFoundException;
import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.repo.OrderToSupplierRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static capstone.backend.mapper.OrderToSupplierMapper.mapOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static capstone.backend.utils.OrderToSupplierTestUtils.sampleOrder;
import static capstone.backend.utils.OrderToSupplierTestUtils.sampleOrderDTO;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class OrderToSupplierServiceTest {

    private final OrderToSupplierRepo orderRepo = mock(OrderToSupplierRepo.class);
    private final ProductService productService = mock(ProductService.class);
    private final OrderItemService orderItemService = mock(OrderItemService.class);
    private final OrderToSupplierService orderService = new OrderToSupplierService(orderRepo, productService, orderItemService);


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
        OrderItemDTO orderItem = expected.getOrderItems().get(0);
        Long productId = orderToSave.getOrderItems().get(0).getProduct().getId();
        when(orderRepo.save(orderToSave)).thenReturn(orderToSave);
        when(orderRepo.findById(orderToSave.getId())).thenReturn(Optional.empty());
        when(productService.checkIfProductExists(productId)).thenReturn(true);
        when(orderItemService.addOrderItem(orderItem)).thenReturn(orderItem);
        //WHEN
        OrderToSupplierDTO actual = orderService.createOrder(sampleOrderDTO());
        //THEN
        verify(orderRepo).save(orderToSave);
        verify(orderRepo).findById(123L);
        verify(productService).checkIfProductExists(productId);
        verify(orderItemService).addOrderItem(orderItem);
        assertThat(actual, is(expected));
    }

    @Test
    void createOrderThrowsWhenProductNonExistent() {
        //GIVEN
        OrderToSupplierDTO orderToSave = sampleOrderDTO();
        Long productId = orderToSave.getOrderItems().get(0).getProduct().getId();
        when(productService.checkIfProductExists(productId)).thenReturn(false);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(orderToSave));
        assertThat(ex.getMessage(), is("You tried to order a product that doesn't exist!"));
        verify(productService).checkIfProductExists(productId);
    }

    @Test
    void createOrdreThrowsWhenOrderAlreadyExists() {
        //GIVEN
        OrderToSupplierDTO orderToSave = sampleOrderDTO();
        Long productId = orderToSave.getOrderItems().get(0).getProduct().getId();
        when(productService.checkIfProductExists(productId)).thenReturn(true);
        when(orderRepo.findById(orderToSave.getId())).thenReturn(Optional.of(mapOrder(orderToSave)));
        //WHEN - //THEN
        Exception ex = assertThrows(EntityWithThisIdAlreadyExistException.class, () -> orderService.createOrder(orderToSave));
        assertThat(ex.getMessage(), is("An Order with this id already exists!"));
        verify(productService).checkIfProductExists(productId);
        verify(orderRepo).findById(orderToSave.getId());
    }
}
