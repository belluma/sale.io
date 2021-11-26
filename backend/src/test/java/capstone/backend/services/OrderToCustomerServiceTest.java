package capstone.backend.services;

import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.ProductDTO;
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
import static capstone.backend.model.enums.OrderToCustomerStatus.OPEN;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItemDTO;
import static capstone.backend.utils.OrderToCustomerTestUtils.*;
import static capstone.backend.utils.ProductTestUtils.sampleProductDTOWithDetailsWithId;
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
        ProductDTO productToAdd = sampleProductDTOWithDetailsWithId().withAmountInStock(1);
        OrderItemDTO itemToAdd = sampleOrderItemDTO().withProduct(productToAdd);
        when(orderRepo.findById(expected.getId())).thenReturn(Optional.of(emptyOrderOpen()));
        when(orderRepo.existsById(expected.getId())).thenReturn(true);
        when(productService.productExists(itemToAdd.getProduct())).thenReturn(true);
        when(productService.getProductDetails(itemToAdd.getProduct().getId())).thenReturn(itemToAdd.getProduct());
        when(orderItemService.addItemToOrderOrUpdateQuantity(itemToAdd, initialOrder)).thenReturn(itemToAdd);
        when(orderRepo.save(orderWithItemAdded)).thenReturn(orderWithItemAdded);
        //WHEN
        OrderToCustomerDTO actual = orderService.addItemsToOrder(expected.getId(), itemToAdd, initialOrder);
        //THEN
        verify(orderRepo, times(2)).findById(expected.getId());
        verify(orderRepo).existsById(expected.getId());
        verify(productService).productExists(itemToAdd.getProduct());
        verify(productService).getProductDetails((itemToAdd.getProduct().getId()));
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
    void addItemsToOrderFailsWhenNotEnoughStock() {
        //GIVEN
        OrderToCustomerDTO initialOrder = emptyOrderDTOWithStatusOpen();
        OrderToCustomerDTO expected = orderDTOWithStatusOpenWithOrderItem();
        OrderToCustomer orderWithItemAdded = orderOpenWithOrderItem();
        OrderItemDTO itemToAdd = sampleOrderItemDTO();
        when(orderRepo.findById(expected.getId())).thenReturn(Optional.of(emptyOrderOpen()));
        when(orderRepo.existsById(expected.getId())).thenReturn(true);
        when(productService.productExists(itemToAdd.getProduct())).thenReturn(true);
        when(productService.getProductDetails(itemToAdd.getProduct().getId())).thenReturn(itemToAdd.getProduct());
        when(orderItemService.addItemToOrderOrUpdateQuantity(itemToAdd, initialOrder)).thenReturn(itemToAdd);
        when(orderRepo.save(orderWithItemAdded)).thenReturn(orderWithItemAdded);
        Long orderId = expected.getId();
        //WHEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.addItemsToOrder(orderId, itemToAdd, initialOrder));
        //THEN
        verify(orderRepo).findById(expected.getId());
        verify(orderRepo).existsById(expected.getId());
        verify(productService).productExists(itemToAdd.getProduct());
        verify(productService).getProductDetails(itemToAdd.getProduct().getId());
        assertThat(ex.getMessage(), is("Not enough items in stock!"));
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
    void cashoutOrderFailsWhenOrderNonExistent() {
        //GIVEN
        OrderToCustomerDTO orderToCashout = orderDTOWithStatusOpenWithOrderItem();
        when(orderRepo.findById(orderToCashout.getId())).thenReturn(Optional.empty());
        //WHEN - //THEN
        assertThrows(EntityNotFoundException.class, () -> orderService.cashoutOrder(orderToCashout));
    }

    @Test
    void cashoutOrderFailsWhenOrderAlreadyPaid() {
        //GIVEN
        OrderToCustomerDTO orderToCashout = orderDTOWithStatusOpenWithOrderItem();
        when(orderRepo.findById(orderToCashout.getId())).thenReturn(Optional.of(orderPaidWithOrderItem()));
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.cashoutOrder(orderToCashout));
        assertThat(ex.getMessage(), is("This order has already been cashed out!"));
    }

    @Test
    void removeItemEntirelyFromOrder() {
        //GIVEN
        OrderItemDTO orderItem = sampleOrderItemDTO();
        OrderToCustomerDTO order = orderDTOWithStatusOpenWithOrderItem();
        OrderToCustomerDTO expected = emptyOrderDTOWithStatusOpen();
        Long orderId = order.getId();
        when(orderRepo.existsById(orderId)).thenReturn(true);
        when(orderRepo.findById(orderId)).thenReturn(Optional.of(mapOrder(order)));
        when(orderRepo.save(mapOrder(expected))).thenReturn(mapOrder(expected));
        when(orderItemService.itemAlreadyOnOrder(orderItem, order)).thenReturn(Optional.of(orderItem));
        when(productService.productExists(orderItem.getProduct())).thenReturn(true);
        doAnswer(i -> {
            OrderToCustomerDTO orderToReduceFrom = i.getArgument(1);
            orderToReduceFrom.setOrderItems(List.of());
            return null;
        }).when(orderItemService).reduceQuantityOfOrderItem(orderItem, order);
        doAnswer(i -> {
            OrderItem orderItemThatHasBeenReduced = i.getArgument(0);
            orderItemThatHasBeenReduced.getProduct().setAmountInStock(0);
            return null;
        }).when(productService).resetAmountInStockWhenRemovingFromBill(mapOrderItem(orderItem));
        //WHEN
        OrderToCustomerDTO actual = orderService.removeItemsFromOrder(orderId, orderItem, order);
        //THEN
        verify(orderRepo).existsById(orderId);
        verify(orderRepo, times(2)).findById(orderId);
        verify(orderItemService).itemAlreadyOnOrder(orderItem, order);
        verify(productService).productExists(orderItem.getProduct());
        verify(orderItemService).reduceQuantityOfOrderItem(orderItem, order);
        verify(productService).resetAmountInStockWhenRemovingFromBill(mapOrderItem(orderItem));
        assertThat(actual, is(expected));
        assertThat(orderItem.getProduct().getAmountInStock(), is(0));
    }

    @Test
    void deductAmountOnAnOrderWithSeveralItems() {
        //GIVEN
        OrderItemDTO orderItem = sampleOrderItemDTO();
        OrderToCustomerDTO order = orderDTOWithThreeItemsAndStatusOpen();
        OrderToCustomerDTO expected = orderDTOWithTwoItemsAndStatusOpen();
        Long orderId = order.getId();
        when(productService.productExists(any())).thenReturn(true);
        when(orderRepo.existsById(orderId)).thenReturn(true);
        when(orderRepo.findById(orderId)).thenReturn(Optional.of(mapOrder(order)));
        when(orderRepo.save(mapOrder(expected))).thenReturn(mapOrder(expected));
        when(orderItemService.itemAlreadyOnOrder(orderItem, order)).thenReturn(Optional.of(orderItem));
        doAnswer(i -> {
            OrderToCustomerDTO orderToReduceFrom = i.getArgument(1);
            orderToReduceFrom.setOrderItems(expected.getOrderItems());
            return null;
        }).when(orderItemService).reduceQuantityOfOrderItem(orderItem, order);
        doAnswer(i -> {
            OrderItem orderItemThatHasBeenReduced = i.getArgument(0);
            orderItemThatHasBeenReduced.getProduct().setAmountInStock(0);
            return null;
        }).when(productService).resetAmountInStockWhenRemovingFromBill(mapOrderItem(orderItem));
        //WHEN
        OrderToCustomerDTO actual = orderService.removeItemsFromOrder(orderId, orderItem, order);
        //THEN
        verify(orderRepo).existsById(orderId);
        verify(orderRepo, times(2)).findById(orderId);
        verify(orderItemService).itemAlreadyOnOrder(orderItem, order);
        verify(productService).productExists(orderItem.getProduct());
        verify(orderItemService).reduceQuantityOfOrderItem(orderItem, order);
        verify(productService).resetAmountInStockWhenRemovingFromBill(mapOrderItem(orderItem));
        assertThat(actual, is(expected));
        assertThat(orderItem.getProduct().getAmountInStock(), is(0));
    }

    @Test
    void removeItemsFromOrderFailsWhenOrderNonExistent() {
        //GIVEN
        OrderItemDTO orderItem = sampleOrderItemDTO();
        OrderToCustomerDTO order = orderDTOWithThreeItemsAndStatusOpen();
        when(orderRepo.existsById(order.getId())).thenReturn(false);
        Long orderId = order.getId();
        //WHEN - //THEN
        Exception ex = assertThrows(EntityNotFoundException.class, () -> orderService.removeItemsFromOrder(orderId, orderItem, order));
        assertThat(ex.getMessage(), is("You're trying to remove from an order that doesn't exist"));
        verify(orderRepo).existsById(order.getId());
    }

    @Test
    void removeItemsFailsWhenOrderAlreadyPaid() {
        OrderToCustomerDTO order = orderDTOWithStatusOpenWithOrderItem();
        OrderItemDTO orderItem = sampleOrderItemDTO();
        when(orderRepo.existsById(order.getId())).thenReturn(true);
        when(orderRepo.findById(order.getId())).thenReturn(Optional.of(orderPaidWithOrderItem()));
        Long orderId = order.getId();
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.removeItemsFromOrder(orderId, orderItem, order));
        assertThat(ex.getMessage(), is("This order has already been cashed out!"));
        verify(orderRepo).existsById(order.getId());
        verify(orderRepo).findById(order.getId());
    }

    @Test
    void removeItemsFailsWhenItemNotOnOrder() {
        //GIVEN
        OrderToCustomerDTO order = orderDTOWithStatusOpenWithOrderItem();
        OrderItemDTO orderItem = sampleOrderItemDTO();
        when(orderRepo.existsById(order.getId())).thenReturn(true);
        when(orderRepo.findById(order.getId())).thenReturn(Optional.of(mapOrder(order)));
        when(orderItemService.itemAlreadyOnOrder(orderItem, order)).thenReturn(Optional.empty());
        Long orderId = order.getId();
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.removeItemsFromOrder(orderId, orderItem, order));
        assertThat(ex.getMessage(), is("The item you're trying to remove is not on the order"));
        verify(orderRepo).existsById(order.getId());
        verify(orderRepo).findById(order.getId());
        verify(orderItemService).itemAlreadyOnOrder(orderItem, order);
    }

    @Test
    void removeItemsFailsWhenProductDoesNotExist() {
        //GIVEN
        OrderToCustomerDTO order = orderDTOWithStatusOpenWithOrderItem();
        OrderItemDTO orderItem = sampleOrderItemDTO();
        when(orderRepo.existsById(order.getId())).thenReturn(true);
        when(orderRepo.findById(order.getId())).thenReturn(Optional.of(mapOrder(order)));
        when(orderItemService.itemAlreadyOnOrder(orderItem, order)).thenReturn(Optional.of(orderItem));
        when(productService.productExists(orderItem.getProduct())).thenReturn(false);
        Long orderId = order.getId();
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.removeItemsFromOrder(orderId, orderItem, order));
        assertThat(ex.getMessage(), is("You're trying to remove a product that doesn't exist"));
        verify(orderRepo).existsById(order.getId());
        verify(orderRepo).findById(order.getId());
        verify(orderItemService).itemAlreadyOnOrder(orderItem, order);
        verify(productService).productExists(orderItem.getProduct());
    }

    @Test
    void removeItemsFailsWhenTryingToRemoveMoreItemsThanOnList() {
        //GIVEN
        OrderItemDTO orderItem = sampleOrderItemDTO().withQuantity(2);
        OrderToCustomerDTO order = orderDTOWithStatusOpenWithOrderItem();
        OrderToCustomerDTO expected = emptyOrderDTOWithStatusOpen();
        Long orderId = order.getId();
        when(orderRepo.existsById(orderId)).thenReturn(true);
        when(orderRepo.findById(orderId)).thenReturn(Optional.of(mapOrder(order)));
        when(orderRepo.save(mapOrder(expected))).thenReturn(mapOrder(expected));
        when(orderItemService.itemAlreadyOnOrder(orderItem, order)).thenReturn(Optional.of(orderItem));
        when(productService.productExists(orderItem.getProduct())).thenReturn(true);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.removeItemsFromOrder(orderId, orderItem, order));
        assertThat(ex.getMessage(), is("It's not possible to remove more items than are on the order"));
        verify(orderRepo).existsById(orderId);
        verify(orderRepo).findById(orderId);
        verify(orderItemService).itemAlreadyOnOrder(orderItem, order);
        verify(productService).productExists(orderItem.getProduct());
    }
}
