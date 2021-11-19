package capstone.backend.services;

import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.model.dto.order.OrderToCustomerDTO;
import capstone.backend.repo.OrderToCustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static capstone.backend.mapper.OrderItemMapper.mapOrderItem;
import static capstone.backend.mapper.OrderToCustomerMapper.mapOrder;
import static capstone.backend.model.enums.OrderStatus.OPEN;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItem;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItemDTO;
import static capstone.backend.utils.OrderToCustomerTestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class OrderToCustomerServiceTest {

    private final OrderToCustomerRepo orderRepo = mock(OrderToCustomerRepo.class);
    private final OrderItemService orderItemService = mock(OrderItemService.class);
    private final ProductService productService = mock(ProductService.class);
    private final OrderToCustomerService orderService = new OrderToCustomerService(orderRepo, productService, orderItemService);


    @Test
    void getAllOrders() {
        //GIVEN
        List<OrderToCustomerDTO> expected = List.of(emptyOrderDTOWithStatusOpen(), orderDTOWithStatusPaidWithOrderItem());
        when(orderRepo.findAll()).thenReturn(List.of(emptyOrderOpen(), orderPaidWithOrderItem()));
        //WHEN
        List<OrderToCustomerDTO> actual = orderService.getAllOrders();
        //THEN
        assertIterableEquals(expected, actual);
    }

    @Test
    void getAllOpenOrders() {
        //GIVEN
        List<OrderToCustomerDTO> expected = List.of(emptyOrderDTOWithStatusOpen());
        when(orderRepo.findAllByStatus(OPEN)).thenReturn(List.of(emptyOrderOpen()));
        //WHEN
        List<OrderToCustomerDTO> actual = orderService.getAllOpenOrders();
        //THEN
        assertIterableEquals(expected, actual);
    }

    @Test
    void createEmptyOrder() {
        //GIVEN
        OrderToCustomerDTO expected = emptyOrderDTOWithStatusOpen();
        when(orderRepo.save(new OrderToCustomer(OPEN))).thenReturn(emptyOrderOpen());
        //WHEN
        OrderToCustomerDTO actual = orderService.createEmptyOrder();
        // THEN
        assertThat(actual, is(expected));
    }

    @Test
    void addItemsToOrder() {
        //GIVEN
        OrderToCustomerDTO initialOrder = emptyOrderDTOWithStatusOpen();
        OrderToCustomerDTO expected = orderDTOWithStatusOpenWithOrderItem();
        OrderToCustomer orderWithItemAdded = orderOpenWithOrderItem();
        OrderItemDTO itemToAdd = sampleOrderItemDTO();
        when(orderRepo.findById(expected.getId())).thenReturn(Optional.of(emptyOrderOpen()));
        when(orderRepo.existsById(expected.getId())).thenReturn(true);
        when(productService.productExists(itemToAdd.getProduct())).thenReturn(true);
        when(orderItemService.addItemToOrderOrUpdateQuantity(itemToAdd, initialOrder)).thenReturn(itemToAdd);
        when(orderRepo.save(orderWithItemAdded)).thenReturn(orderWithItemAdded);
        //WHEN
        OrderToCustomerDTO actual = orderService.addItemsToOrder(expected.getId(), itemToAdd, initialOrder);
        //THEN
        verify(orderRepo, times(2)).findById(expected.getId());
        verify(orderRepo).existsById(expected.getId());
        verify(productService).productExists(itemToAdd.getProduct());
        verify(orderItemService).addItemToOrderOrUpdateQuantity(itemToAdd, initialOrder);
        verify(orderRepo).save(orderWithItemAdded);
        assertThat(actual, is(expected));
    }

    @Test
    void addItemsToOrderFailsWhenOrderDoesNotExist() {
        //GIVEN
        OrderToCustomer nonExistentOrder = emptyOrderOpen();
        OrderToCustomerDTO toOrder = mapOrder(nonExistentOrder);
        Long orderId = nonExistentOrder.getId();
        OrderItemDTO item = sampleOrderItemDTO();
        when(orderRepo.existsById(nonExistentOrder.getId())).thenReturn(false);
        //WHEN - //THEN
        Exception ex = assertThrows(EntityNotFoundException.class, () -> orderService.addItemsToOrder(orderId, item, toOrder));
        assertThat(ex.getMessage(), is("You're trying to add to an order that doesn't exist"));
        verify(orderRepo).existsById(nonExistentOrder.getId());
    }

    @Test
    void addItemsToOrderFailsWhenOrderAlreadyPaid() {
        //GIVEN
        OrderToCustomer paidOrder = orderPaidWithOrderItem();
        OrderToCustomerDTO toOrder = mapOrder(paidOrder);
        Long orderId = paidOrder.getId();
        OrderItemDTO item = sampleOrderItemDTO();
        when(orderRepo.findById(paidOrder.getId())).thenReturn(Optional.of(paidOrder));
        when(orderRepo.existsById(paidOrder.getId())).thenReturn(true);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.addItemsToOrder(orderId, item, toOrder));
        assertThat(ex.getMessage(), is("This order has already been cashed out!"));
        verify(orderRepo).existsById(paidOrder.getId());
        verify(orderRepo).findById(paidOrder.getId());
    }

    @Test
    void addItemsToOrderFailsWhenProductDoesNotExist() {
        //GIVEN
        OrderToCustomerDTO openOrder = orderDTOWithStatusOpenWithOrderItem();
        OrderItemDTO itemWithNonExistentProduct = sampleOrderItemDTO();
        Long orderId = openOrder.getId();
        when(orderRepo.findById(openOrder.getId())).thenReturn(Optional.of(emptyOrderOpen()));
        when(productService.productExists(itemWithNonExistentProduct.getProduct())).thenReturn(false);
        when(orderRepo.existsById(openOrder.getId())).thenReturn(true);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.addItemsToOrder(orderId, itemWithNonExistentProduct, openOrder));
        assertThat(ex.getMessage(), is("You're trying to add a product that doesn't exist"));
        verify(orderRepo).existsById(openOrder.getId());
        verify(orderRepo).findById(openOrder.getId());
        verify(productService).productExists(itemWithNonExistentProduct.getProduct());
    }

    @Test
    void cashoutOrder() {
        //GIVEN
        OrderToCustomerDTO orderToCashout = orderDTOWithStatusOpenWithOrderItem();
        OrderToCustomerDTO expected = orderDTOWithStatusPaidWithOrderItem();
        when(orderRepo.findById(orderToCashout.getId())).thenReturn(Optional.of(mapOrder(orderToCashout)));
        when(orderRepo.save(orderPaidWithOrderItem())).thenReturn(orderPaidWithOrderItem());
        //WHEN
        OrderToCustomerDTO actual = orderService.cashoutOrder(orderToCashout);
        //THEN
        assertThat(actual, is(expected));
        verify(orderRepo, times(2)).findById(orderToCashout.getId());
        verify(orderRepo).save(orderPaidWithOrderItem());
    }
    @Test
    void cashoutOrderFailsWhenOrderNonExistent(){
        //GIVEN
        OrderToCustomerDTO orderToCashout = orderDTOWithStatusOpenWithOrderItem();
        when(orderRepo.findById(orderToCashout.getId())).thenReturn(Optional.empty());
        //WHEN - //THEN
        assertThrows(EntityNotFoundException.class, () -> orderService.cashoutOrder(orderToCashout));
    }
    @Test
    void cashoutOrderFailsWhenOrderAlreadyPaid(){
        //GIVEN
        OrderToCustomerDTO orderToCashout = orderDTOWithStatusOpenWithOrderItem();
        when(orderRepo.findById(orderToCashout.getId())).thenReturn(Optional.of(orderPaidWithOrderItem()));
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.cashoutOrder(orderToCashout));
        assertThat(ex.getMessage(), is("This order has already been cashed out!"));
    }

    @Test
    void removeItemEntirelyFromOrder(){
        //GIVEN
        OrderItemDTO orderItem = sampleOrderItemDTO();
    OrderToCustomerDTO order = orderDTOWithStatusOpenWithOrderItem();

        //WHEN


        //THEN


    }

}
