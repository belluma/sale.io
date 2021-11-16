package capstone.backend.controller;

import capstone.backend.model.db.Product;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.model.enums.OrderStatus;
import capstone.backend.repo.OrderItemRepo;
import capstone.backend.repo.OrderToSupplierRepo;
import capstone.backend.repo.ProductRepo;
import capstone.backend.repo.SupplierRepo;
import capstone.backend.utils.ControllerTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static capstone.backend.mapper.OrderItemMapper.mapOrderItem;
import static capstone.backend.mapper.OrderToSupplierMapper.mapOrder;
import static capstone.backend.mapper.SupplierMapper.mapSupplier;
import static capstone.backend.model.enums.OrderStatus.PENDING;
import static capstone.backend.model.enums.OrderStatus.RECEIVED;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItem;
import static capstone.backend.utils.ProductTestUtils.sampleProduct;
import static capstone.backend.utils.ProductTestUtils.sampleProductWithId;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderToSupplierControllerTest {

    @Autowired
    OrderToSupplierController controller;
    @Autowired
    OrderToSupplierRepo orderRepo;
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
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withDatabaseName("pos")
            .withUsername("pos")
            .withPassword("pos");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @BeforeEach
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
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(List.of(supplier)));
        OrderItem orderItem = orderItemRepo.save(sampleOrderItem().withProduct(product));
        OrderToSupplier order = orderRepo.save(new OrderToSupplier(1L, List.of(orderItem), PENDING, supplier));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<OrderToSupplierDTO[]> response = restTemplate.exchange(BASEURL, HttpMethod.GET, new HttpEntity<>(headers), OrderToSupplierDTO[].class);
        OrderToSupplierDTO expected = mapOrder(order);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(Arrays.asList(response.getBody()), List.of(expected));
    }

    @Test
    void createOrder() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(List.of(sampleSupplier)));
        OrderItem orderItem = new OrderItem(product, 1);
        OrderToSupplierDTO order = new OrderToSupplierDTO(1L, List.of(mapOrderItem(orderItem)), mapSupplier(sampleSupplier));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<OrderToSupplierDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(order, headers), OrderToSupplierDTO.class);
        order.setId(response.getBody().getId());
        order.setStatus(PENDING);
        order.getOrderItems().get(0).setId(response.getBody().getOrderItems().get(0).getId());
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(order));
        assertThat(orderRepo.findAll().size(), is(1));
        assertThat(orderRepo.findById(order.getId()).get(), is(mapOrder(order)));
    }

    @Test
    void createOrderSavesOrderItem() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(List.of(sampleSupplier)));
        OrderItem orderItem = new OrderItem(product, 1);
        OrderToSupplierDTO order = new OrderToSupplierDTO(1L, List.of(mapOrderItem(orderItem)), mapSupplier(sampleSupplier));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<OrderToSupplierDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(order, headers), OrderToSupplierDTO.class);
        Long orderItemId = response.getBody().getOrderItems().get(0).getId();
        orderItem.setId(orderItemId);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(orderItemRepo.findAll().size(), is(1));
        assertThat(orderItemRepo.findById(orderItemId).get(), is(orderItem));
    }

    @Test
    void createOrderReturnsNotAcceptableWhenProductNonExistent() {
        //GIVEN
        Product product = sampleProductWithId();
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        OrderItem orderItem = new OrderItem(product, 1);
        OrderToSupplierDTO order = new OrderToSupplierDTO(1L, List.of(mapOrderItem(orderItem)), mapSupplier(sampleSupplier));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<OrderToSupplierDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(order, headers), OrderToSupplierDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    void createOrderReturnsNotAcceptableWhenOrderIdAlreadyExists() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(List.of(sampleSupplier)));
        OrderItem firstOrderItem = orderItemRepo.save(new OrderItem(product, 1));
        OrderItem orderItem = new OrderItem(product, 1);
        OrderToSupplier firstOrder = orderRepo.save(new OrderToSupplier((1L), List.of(firstOrderItem), PENDING, sampleSupplier));
        OrderToSupplierDTO order = new OrderToSupplierDTO(firstOrder.getId(), List.of(mapOrderItem(orderItem)), mapSupplier(sampleSupplier));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<OrderToSupplierDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(order, headers), OrderToSupplierDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    void createOrderReturnsNotAcceptableWhenSupplierDoesNotCarryProduct() {
        //GIVEN
        Supplier supplierToOrderFrom = supplierRepo.save(sampleSupplier());
        Supplier supplierToAssociateProductWith = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(List.of(supplierToAssociateProductWith)));
        OrderItem orderItem = new OrderItem(product, 1);
        OrderToSupplierDTO order = new OrderToSupplierDTO(1L, List.of(mapOrderItem(orderItem)), mapSupplier(supplierToOrderFrom));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<OrderToSupplierDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(order, headers), OrderToSupplierDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    void receiveOrder() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(List.of(sampleSupplier)));
        OrderItem orderItem = orderItemRepo.save(sampleOrderItem().withProduct(product));
        OrderToSupplier pendingOrder = orderRepo.save(new OrderToSupplier(1L, List.of(orderItem), PENDING, sampleSupplier));
        OrderToSupplierDTO orderToReceive = mapOrder(pendingOrder);
        OrderToSupplierDTO receivedOrder = mapOrder(pendingOrder);
        product.setAmountInStock(1);
        receivedOrder.setStatus(RECEIVED);
        receivedOrder.setOrderItems(List.of(mapOrderItem(orderItem.withProduct(product))));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + String.format("/?id=%d&status=RECEIVED", pendingOrder.getId() );
        //WHEN
        ResponseEntity<OrderToSupplierDTO> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(orderToReceive, headers), OrderToSupplierDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(receivedOrder));
        assertThat(productRepo.findById(product.getId()).get().getAmountInStock(), is(receivedOrder.getOrderItems().get(0).getQuantity()));
    }
}
