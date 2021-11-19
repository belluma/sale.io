package capstone.backend.services;

import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.repo.OrderToSupplierRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static capstone.backend.mapper.OrderToSupplierMapper.mapOrder;
import static capstone.backend.model.enums.OrderToSupplierStatus.PENDING;
import static capstone.backend.model.enums.OrderToSupplierStatus.RECEIVED;
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
        ProductDTO product = expected.getOrderItems().get(0).getProduct();
        Long supplierId = orderToSave.getSupplier().getId();
        when(orderRepo.save(orderToSave)).thenReturn(orderToSave);
        when(orderRepo.existsById(orderToSave.getId())).thenReturn(false);
        when(productService.productExists(product)).thenReturn(true);
        when(orderItemService.addOrderItem(orderItem)).thenReturn(orderItem);
        when(supplierService.supplierExists(supplierId)).thenReturn(true);
        //WHEN
        OrderToSupplierDTO actual = orderService.createOrder(sampleOrderDTO());
        //THEN
        verify(orderRepo).save(orderToSave);
        verify(orderRepo).existsById(123L);
        verify(productService).productExists(product);
        verify(supplierService).supplierExists(supplierId);
        verify(orderItemService).addOrderItem(orderItem);
        assertThat(actual, is(expected));
    }

    @Test
    void createOrderThrowsWhenProductNonExistent() {
        //GIVEN
        OrderToSupplierDTO orderToSave = sampleOrderDTO();
        ProductDTO product = orderToSave.getOrderItems().get(0).getProduct();
        when(productService.productExists(product)).thenReturn(false);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(orderToSave));
        assertThat(ex.getMessage(), is("You tried to order a product that doesn't exist!"));
        verify(productService).productExists(product);
    }

    @Test
    void createOrderThrowsWhenOrderAlreadyExists() {
        //GIVEN
        OrderToSupplierDTO orderToSave = sampleOrderDTO();
        when(orderRepo.existsById(orderToSave.getId())).thenReturn(true);
        //WHEN - //THEN
        Exception ex = assertThrows(EntityWithThisIdAlreadyExistException.class, () -> orderService.createOrder(orderToSave));
        assertThat(ex.getMessage(), is(String.format("An order with id %d already exists!", orderToSave.getId())));
        verify(orderRepo).existsById(orderToSave.getId());
    }

    @Test
    void createOrderThrowsWhenSupplierNonExitent() {
        //GIVEN
        OrderToSupplierDTO orderToSave = sampleOrderDTO();
        ProductDTO product = orderToSave.getOrderItems().get(0).getProduct();
        Long supplierId = orderToSave.getSupplier().getId();
        when(productService.productExists(product)).thenReturn(true);
        when(orderRepo.existsById(orderToSave.getId())).thenReturn(false);
        when(supplierService.supplierExists(supplierId)).thenReturn(false);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(orderToSave));
        assertThat(ex.getMessage(), is("You tried to order from a supplier that doesn't exist!"));
        verify(productService).productExists(product);
        verify(orderRepo).existsById(orderToSave.getId());
        verify(supplierService).supplierExists(supplierId);
    }

    @Test
    void createOrderThrowsWhenProductOrderedIsNotCarrriedBySupplier() {
        //GIVEN
        OrderToSupplierDTO orderToSave = sampleOrderDTO();
        ProductDTO product = orderToSave.getOrderItems().get(0).getProduct();
        Long supplierId = orderToSave.getSupplier().getId() + 1;
        orderToSave.getSupplier().setId(supplierId);
        when(productService.productExists(product)).thenReturn(true);
        when(orderRepo.existsById(orderToSave.getId())).thenReturn(false);
        when(supplierService.supplierExists(supplierId)).thenReturn(true);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(orderToSave));
        assertThat(ex.getMessage(), is("The supplier doesn't carry one or several of the items you tried to order!"));
        verify(productService).productExists(product);
        verify(orderRepo).existsById(orderToSave.getId());
        verify(supplierService).supplierExists(supplierId);
    }

    @Test
    void receiveOrder() {
        //GIVEN
        OrderToSupplierDTO orderToReceive = sampleOrderDTOWithStatusPending();
        OrderToSupplierDTO expected = sampleOrderDTOWithStatusReceived();
        expected.setStatus(RECEIVED);
        ProductDTO product = expected.getOrderItems().get(0).getProduct();
        when(orderRepo.existsById(orderToReceive.getId())).thenReturn(true);
        when(orderRepo.getById(orderToReceive.getId())).thenReturn(mapOrder(orderToReceive));
        when(productService.productExists(product)).thenReturn(true);
        when(orderRepo.save(mapOrder(orderToReceive))).thenReturn(mapOrder(expected));
        //WHEN
        OrderToSupplierDTO actual = orderService.receiveOrder(orderToReceive, RECEIVED);
        //THEN
        verify(orderRepo).existsById(123L);
        verify(productService).receiveGoods(orderToReceive.getOrderItems());
        verify(orderRepo).getById(123L);
        assertThat(actual, is(expected));
    }

    @Test
    void receiveOrderThrowsWhenOrderNonExistent() {
        //GIVEN
        OrderToSupplierDTO nonExistentOrder = sampleOrderDTOWithStatusPending();
        when(orderRepo.existsById(nonExistentOrder.getId())).thenReturn(false);
        //WHEN - //THEN
        Exception ex = assertThrows(EntityNotFoundException.class, () -> orderService.receiveOrder(nonExistentOrder, RECEIVED));
        assertThat(ex.getMessage(), is("The order you're trying to receive doesn't exist"));
        verify(orderRepo).existsById(nonExistentOrder.getId());
    }

    @Test
    void receiveOrderThrowsWhenParamWrong() {
        //GIVEN
        OrderToSupplierDTO order = sampleOrderDTOWithStatusPending();
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.receiveOrder(order, PENDING));
        assertThat(ex.getMessage(), is("Your request couldn't be processed!"));
    }

    @Test
    void receiveOrderThrowsWhenNonExitentProductInOrder(){
        //GIVEN
        OrderToSupplierDTO orderWithNonExistentProduct = sampleOrderDTOWithStatusPending();
        ProductDTO product = orderWithNonExistentProduct.getOrderItems().get(0).getProduct();
        when(orderRepo.existsById(orderWithNonExistentProduct.getId())).thenReturn(true);
        when(orderRepo.getById(orderWithNonExistentProduct.getId())).thenReturn(mapOrder(orderWithNonExistentProduct));
        when(productService.productExists(product)).thenReturn(false);
        //WHEN -  /THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.receiveOrder(orderWithNonExistentProduct, RECEIVED));
        assertThat(ex.getMessage(), is("Your order contains products that don't exist in your database"));
    }
}
