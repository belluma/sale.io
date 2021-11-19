package capstone.backend.services;

import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderToCustomerDTO;
import capstone.backend.model.enums.OrderStatus;
import capstone.backend.repo.OrderToCustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static capstone.backend.mapper.OrderToCustomerMapper.mapOrder;
import static capstone.backend.utils.OrderToCustomerTestUtils.emptyOrderDTOWithStatusOpen;
import static capstone.backend.utils.OrderToCustomerTestUtils.emptyOrderOpen;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class OrderToCustomerServiceTest {

    private final OrderToCustomerRepo orderRepo = mock(OrderToCustomerRepo.class);
    private final OrderItemService orderItemService = mock(OrderItemService.class);
    private final ProductService productService = mock(ProductService.class);
    private final OrderToCustomerService orderService = new OrderToCustomerService(orderRepo, productService, orderItemService);


    @Test
    void getAllOrders() {
    }

    @Test
    void createEmptyOrder(){
        //GIVEN
        OrderToCustomerDTO expected = emptyOrderDTOWithStatusOpen();
        when(orderRepo.save(new OrderToCustomer(OrderStatus.OPEN))).thenReturn(emptyOrderOpen());
        //WHEN
        OrderToCustomerDTO actual = orderService.createEmptyOrder();
        // THEN
        assertThat(actual, is(expected));
    }

    @Test
    void addItemsToOrder() {
    }

    @Test
    void cashoutOrder() {
    }



}
