package capstone.backend.controller;

import capstone.backend.CombinedTestContainer;
import capstone.backend.repo.*;
import capstone.backend.utils.ControllerTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
