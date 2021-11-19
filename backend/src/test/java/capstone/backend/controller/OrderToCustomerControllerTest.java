package capstone.backend.controller;

import capstone.backend.CombinedTestContainer;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.order.OrderToCustomerDTO;
import capstone.backend.repo.*;
import capstone.backend.utils.ControllerTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static capstone.backend.mapper.OrderToCustomerMapper.mapOrder;
import static capstone.backend.model.enums.OrderToCustomerStatus.OPEN;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItem;
import static capstone.backend.utils.OrderToCustomerTestUtils.orderPaidWithOrderItem;
import static capstone.backend.utils.ProductTestUtils.sampleProduct;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderToCustomerControllerTest {

    @Autowired
    OrderToCustomerController controller;
    @Autowired
    OrderToCustomerRepo orderRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    OrderItemRepo orderItemRepo;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    ControllerTestUtils utils;
    String BASEURL = "/api/orders_suppliers";

    @Container
    public static PostgreSQLContainer<CombinedTestContainer> container = CombinedTestContainer.getInstance();

    @BeforeEach
    public void createSupplierAndProduct(){

    }

    @AfterEach
    public void clearDB() {
        orderRepo.deleteAll();
        orderItemRepo.deleteAll();
        productRepo.deleteAll();
        supplierRepo.deleteAll();
    }

    @Test
    void containerIsRunning() {
        assertTrue(container.isRunning());
    }

    @Test
    void getAllOrders() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)));
        OrderItem orderItem = orderItemRepo.save(sampleOrderItem().withProduct(product));
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(1l, List.of(orderItem), OPEN));
        OrderToCustomer order2 = orderRepo.save(orderPaidWithOrderItem());
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<OrderToCustomerDTO[]> response = restTemplate.exchange(BASEURL, HttpMethod.GET, new HttpEntity<>(headers), OrderToCustomerDTO[].class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(List.of(mapOrder(order1), mapOrder(order2)), Arrays.asList(response.getBody()));
        assertThat(orderRepo.findAll().size(), is(2));
    }

    @Test
    void getAllOpenOrders() {
    }

    @Test
    void createOrder() {
    }

    @Test
    void addItemsToOrder() {
    }

    @Test
    void removeItemsFromOrder() {
    }
}
