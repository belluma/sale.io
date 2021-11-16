package capstone.backend.services;

import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
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
import static capstone.backend.utils.OrderToSupplierTestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class OrderToSupplierServiceTest {

    private final OrderToSupplierRepo orderRepo = mock(OrderToSupplierRepo.class);
    private final ProductService productService = mock(ProductService.class);
    private final OrderItemService orderItemService = mock(OrderItemService.class);
    private final SupplierService supplierService = mock(SupplierService.class);
    private final OrderToSupplierService orderService = new OrderToSupplierService(orderRepo, productService, orderItemService, supplierService);


    @Test
    void getAllOrders() {
        //GIVEN
        when(orderRepo.findAll()).thenReturn(List.of(sampleOrderPending()));
        List<OrderToSupplierDTO> expected = List.of(sampleOrderDTOWithStatusPending());
        //WHEN
        List<OrderToSupplierDTO> actual = orderService.getAllOrders();
        //THEN
        assertIterableEquals(actual, expected);
        verify(orderRepo).findAll();
    }

    @Test
    void createOrder() {
        //GIVEN
        OrderToSupplier orderToSave = sampleOrderPending();
        OrderToSupplierDTO expected = sampleOrderDTOWithStatusPending();
        OrderItemDTO orderItem = expected.getOrderItems().get(0);
        Long productId = orderToSave.getOrderItems().get(0).getProduct().getId();
        Long supplierId = orderToSave.getSupplier().getId();
        when(orderRepo.save(orderToSave)).thenReturn(orderToSave);
        when(orderRepo.existsById(orderToSave.getId())).thenReturn(false);
        when(productService.checkIfProductExists(productId)).thenReturn(true);
        when(orderItemService.addOrderItem(orderItem)).thenReturn(orderItem);
        when(supplierService.checkIfSupplierExists(supplierId)).thenReturn(true);
        //WHEN
        OrderToSupplierDTO actual = orderService.createOrder(sampleOrderDTO());
        //THEN
        verify(orderRepo).save(orderToSave);
        verify(orderRepo).existsById(123L);
        verify(productService).checkIfProductExists(productId);
        verify(supplierService).checkIfSupplierExists(supplierId);
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
    void createOrderThrowsWhenOrderAlreadyExists() {
        //GIVEN
        OrderToSupplierDTO orderToSave = sampleOrderDTO();
        Long productId = orderToSave.getOrderItems().get(0).getProduct().getId();
        when(productService.checkIfProductExists(productId)).thenReturn(true);
        when(orderRepo.existsById(orderToSave.getId())).thenReturn(true);
        //WHEN - //THEN
        Exception ex = assertThrows(EntityWithThisIdAlreadyExistException.class, () -> orderService.createOrder(orderToSave));
        assertThat(ex.getMessage(), is("An Order with this id already exists!"));
        verify(productService).checkIfProductExists(productId);
        verify(orderRepo).existsById(orderToSave.getId());
    }
    @Test
    void createOrderThrowsWhenSupplierNonExitent(){
        //GIVEN
        OrderToSupplierDTO orderToSave = sampleOrderDTO();
        Long productId = orderToSave.getOrderItems().get(0).getProduct().getId();
        Long supplierId = orderToSave.getSupplier().getId();
        when(productService.checkIfProductExists(productId)).thenReturn(true);
        when(orderRepo.existsById(orderToSave.getId())).thenReturn(false);
        when(supplierService.checkIfSupplierExists(supplierId)).thenReturn(false);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(orderToSave));
        assertThat(ex.getMessage(), is("You tried to order from a supplier that doesn't exist!"));
        verify(productService).checkIfProductExists(productId);
        verify(orderRepo).existsById(orderToSave.getId());
        verify(supplierService).checkIfSupplierExists(supplierId);
    }
    @Test
    void createOrderThrowsWhenProductOrderedIsNotCarrriedBySupplier(){
        //GIVEN
        OrderToSupplierDTO orderToSave = sampleOrderDTO();
        Long productId = orderToSave.getOrderItems().get(0).getProduct().getId();
        Long supplierId = orderToSave.getSupplier().getId() + 1;
        orderToSave.getSupplier().setId(supplierId);
        when(productService.checkIfProductExists(productId)).thenReturn(true);
        when(orderRepo.existsById(orderToSave.getId())).thenReturn(false);
        when(supplierService.checkIfSupplierExists(supplierId)).thenReturn(true);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(orderToSave));
        assertThat(ex.getMessage(), is("The supplier doesn't carry one or several of the items you tried to order!"));
        verify(productService).checkIfProductExists(productId);
        verify(orderRepo).existsById(orderToSave.getId());
        verify(supplierService).checkIfSupplierExists(supplierId);
    }
}
