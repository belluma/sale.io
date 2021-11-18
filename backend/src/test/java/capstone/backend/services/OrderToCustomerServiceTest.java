package capstone.backend.services;

import capstone.backend.repo.OrderToCustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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
    void createEmptyOrder() {
    }

    @Test
    void addItemsToOrder() {
    }

    @Test
    void cashoutOrder() {
    }
}
