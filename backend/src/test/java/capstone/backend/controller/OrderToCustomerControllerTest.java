package capstone.backend.controller;

import capstone.backend.CombinedTestContainer;
import capstone.backend.exception.CustomError;
import capstone.backend.mapper.OrderItemMapper;
import capstone.backend.mapper.OrderToCustomerMapper;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.dto.order.OrderItemDTO;
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

import java.util.*;

import static capstone.backend.mapper.OrderItemMapper.mapOrderItem;
import static capstone.backend.mapper.ProductMapper.mapProductWithDetails;
import static capstone.backend.model.enums.OrderToCustomerStatus.OPEN;
import static capstone.backend.model.enums.OrderToCustomerStatus.PAID;
import static capstone.backend.utils.OrderItemTestUtils.*;
import static capstone.backend.utils.OrderToCustomerTestUtils.emptyOrderDTOWithStatusOpen;
import static capstone.backend.utils.ProductTestUtils.*;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
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
    String BASEURL = "/api/orders_customers";

    @Container
    public static PostgreSQLContainer<CombinedTestContainer> container = CombinedTestContainer.getInstance();

    @BeforeEach
    public void createSupplierAndProduct() {

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
        OrderItem orderItem2 = orderItemRepo.save(sampleOrderItem().withProduct(product));
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(1L, List.of(orderItem), OPEN));
        OrderToCustomer order2 = orderRepo.save(new OrderToCustomer(1L, List.of(orderItem2), PAID));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<OrderToCustomerDTO[]> response = restTemplate.exchange(BASEURL + "/all", HttpMethod.GET, new HttpEntity<>(headers), OrderToCustomerDTO[].class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(List.of(order1, order2), Arrays.stream(Objects.requireNonNull(response.getBody())).map(OrderToCustomerMapper::mapOrder).toList());
        assertThat(orderRepo.findAll().size(), is(2));
    }

    @Test
    void getAllOpenOrders() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)));
        OrderItem orderItem = orderItemRepo.save(sampleOrderItem().withProduct(product));
        OrderItem orderItem2 = orderItemRepo.save(sampleOrderItem().withProduct(product));
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(1L, List.of(orderItem), OPEN));
        orderRepo.save(new OrderToCustomer(1L, List.of(orderItem2), PAID));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<OrderToCustomerDTO[]> response = restTemplate.exchange(BASEURL, HttpMethod.GET, new HttpEntity<>(headers), OrderToCustomerDTO[].class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(List.of(order1), Arrays.stream(Objects.requireNonNull(response.getBody())).map(OrderToCustomerMapper::mapOrder).toList());
        assertThat(orderRepo.findAll().size(), is(2));
    }

    @Test
    void createOrder() {
        //GIVEN
        OrderToCustomerDTO expected = emptyOrderDTOWithStatusOpen();
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<OrderToCustomerDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(headers), OrderToCustomerDTO.class);
        expected.setId(Objects.requireNonNull(response.getBody()).getId());
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
        assertThat(orderRepo.findAll().size(), is(1));
    }

    @Test
    void addItemsToEmptyOrder() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItem orderItem = sampleOrderItemNoId().withProduct(product);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(OPEN));
        OrderToCustomerDTO expected = new OrderToCustomerDTO(order1.getId(), List.of(mapOrderItem(orderItem)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/add/?id=" + order1.getId();
        //WHEN
        ResponseEntity<OrderToCustomerDTO> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItem), headers), OrderToCustomerDTO.class);
        //THEN
        OrderItemDTO orderItemSavedOnOrder = (Objects.requireNonNull(response.getBody()).getOrderItems().get(0));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
        assertThat(orderItemSavedOnOrder.getQuantity(), is(orderItem.getQuantity()));
        assertThat(orderItemSavedOnOrder.getProduct().getAmountInStock(), is(product.getAmountInStock() - orderItem.getQuantity()));
    }

    @Test
    void addItemsToOrderWithExistingItem() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItem orderItemToAdd = sampleOrderItem().withProduct(product);
        OrderItem orderItemOnOrder = sampleOrderItemNoId().withProduct(product);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(orderItemOnOrder), OPEN));
        OrderToCustomerDTO expected = new OrderToCustomerDTO(order1.getId(), List.of(mapOrderItem(orderItemOnOrder)));
        int expectedNewQty = orderItemToAdd.getQuantity() + orderItemOnOrder.getQuantity();
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/add/?id=" + order1.getId();
        //WHEN
        ResponseEntity<OrderToCustomerDTO> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>( mapOrderItem(orderItemToAdd), headers), OrderToCustomerDTO.class);
        //THEN
        OrderItemDTO orderItemSavedOnOrder = (Objects.requireNonNull(response.getBody()).getOrderItems().get(0));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
        assertThat(orderItemSavedOnOrder.getQuantity(), is(expectedNewQty));
        assertThat(orderItemSavedOnOrder.getProduct().getAmountInStock(), is(product.getAmountInStock() - orderItemToAdd.getQuantity()));
    }

    @Test
    void addItemsToOrderWithMultipleItems() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        Product product2 = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        Product product3 = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        List<OrderItem> orderItems = List.of(sampleOrderItemNoId().withProduct(product),
                sampleOrderItemNoId().withProduct(product2),
                sampleOrderItemNoId().withProduct(product3));
        OrderItem orderItemToAdd = orderItems.get(1);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(orderItems, OPEN));
        OrderToCustomerDTO expected = new OrderToCustomerDTO(order1.getId(), orderItems.stream().map(OrderItemMapper::mapOrderItem).toList());
//        OrderContainerDTO requestBody = new OrderContainerDTO(mapOrder(order1), mapOrderItem(orderItemToAdd));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/add/?id=" + order1.getId();
        //WHEN
        ResponseEntity<OrderToCustomerDTO> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItemToAdd), headers), OrderToCustomerDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()).getOrderItems().get(0).getQuantity(), is(orderItems.get(0).getQuantity()));
        assertThat(Objects.requireNonNull(response.getBody()).getOrderItems().get(1).getQuantity(), is(orderItemToAdd.getQuantity() + orderItems.get(1).getQuantity()));
        assertThat(Objects.requireNonNull(response.getBody()).getOrderItems().get(2).getQuantity(), is(orderItems.get(2).getQuantity()));
        assertThat(response.getBody().getId(), is(expected.getId()));
        assertThat(response.getBody().getOrderItems().size(), is(3));
        assertThat(response.getBody().getOrderItems(), containsInAnyOrder(mapOrderItem(orderItems.get(0)), mapOrderItem(orderItems.get(1)), mapOrderItem(orderItems.get(2))));
        assertThat(orderItemRepo.findAll(), containsInAnyOrder(orderItems.get(0), orderItems.get(1), orderItems.get(2)));
        assertThat(response.getBody().getOrderItems().get(0).getProduct().getAmountInStock(), is(product.getAmountInStock()));
        assertThat(response.getBody().getOrderItems().get(1).getProduct().getAmountInStock(), is(product2.getAmountInStock() - orderItemToAdd.getQuantity()));
        assertThat(response.getBody().getOrderItems().get(2).getProduct().getAmountInStock(), is(product3.getAmountInStock()));
    }

    @Test
    void addItemsToOrderWithHigherQuantity() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(42));
        OrderItem orderItemToAdd = sampleOrderItem().withProduct(product).withQuantity(23);
        OrderItem orderItemOnOrder = sampleOrderItemNoId().withProduct(product);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(orderItemOnOrder), OPEN));
        OrderToCustomerDTO expected = new OrderToCustomerDTO(order1.getId(), List.of(mapOrderItem(orderItemOnOrder)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/add/?id=" + order1.getId();
        //WHEN
        ResponseEntity<OrderToCustomerDTO> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItemToAdd), headers), OrderToCustomerDTO.class);
        //THEN
        OrderItemDTO orderItemSavedOnOrder = (Objects.requireNonNull(response.getBody()).getOrderItems().get(0));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
        assertThat(orderItemSavedOnOrder.getQuantity(), is(orderItemToAdd.getQuantity() + orderItemOnOrder.getQuantity()));
        assertThat(orderItemSavedOnOrder.getProduct().getAmountInStock(), is(product.getAmountInStock() - orderItemToAdd.getQuantity()));
    }

    @Test
    void addItemsFailsWhenOrderDoesNotExist() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItem orderItem = sampleOrderItem().withProduct(product);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(), OPEN));
        order1.setId(12345L);
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/add/?id=" + 12345;
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItem), headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is("You're trying to add to an order that doesn't exist"));
    }

    @Test
    void addItemsFailsWhenOrderAlreadyPaid() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItem orderItemToAdd = sampleOrderItemNoId().withProduct(product);
        OrderItem orderItemOnOrder = sampleOrderItemNoId().withProduct(product);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(orderItemOnOrder), PAID));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/add/?id=" + order1.getId();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItemToAdd), headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is("This order has already been cashed out!"));
    }

    @Test
    void addItemsToOrderFailsWhenProductDoesNotExist() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItem orderItemToAdd = sampleOrderItemNoId();
        OrderItem orderItemOnOrder = sampleOrderItemNoId().withProduct(product);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(orderItemOnOrder), OPEN));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/add/?id=" + order1.getId();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItemToAdd), headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is("You're trying to add a product that doesn't exist"));
    }

    @Test
    void addItemsToOrderFailsWhenAmountInStockTooLow() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)));
        OrderItem orderItem = sampleOrderItem().withProduct(product);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(OPEN));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/add/?id=" + order1.getId();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItem), headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is("Not enough items in stock!"));
    }

    @Test
    void removeItemEntirelyFromOrder() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItem orderItemOnOrder = sampleOrderItemNoId().withProduct(product);
        OrderItem orderItemToTakeOffOrder = sampleOrderItemNoId().withProduct(product);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(orderItemOnOrder), OPEN));
        Long orderItemId = order1.getOrderItems().get(0).getId();
        OrderToCustomerDTO expected = new OrderToCustomerDTO(order1.getId(), List.of());
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/remove/?id=" + order1.getId();
        //WHEN
        ResponseEntity<OrderToCustomerDTO> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItemToTakeOffOrder), headers), OrderToCustomerDTO.class);
        ResponseEntity<Product> returnedProduct = restTemplate.exchange("/api/products/" + product.getId(), HttpMethod.GET, new HttpEntity<>(headers), Product.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
        assertThat(Objects.requireNonNull(returnedProduct.getBody()).getAmountInStock(), is(2));
        assertTrue(orderItemRepo.findById(orderItemId).isEmpty());
    }

    @Test
    void reduceQuantityOnOrder() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        ProductDTO productOnOrderAfterBeingReturned = mapProductWithDetails(product).withAmountInStock(2);
        OrderItemDTO orderItemOnOrder = sampleOrderItemDTONoId().withProduct(productOnOrderAfterBeingReturned).withQuantity(2);
        OrderItem orderItemToTakeOffOrder = sampleOrderItemNoId().withProduct(product);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(mapOrderItem(orderItemOnOrder)), OPEN));
        Long orderItemId = order1.getOrderItems().get(0).getId();
        OrderToCustomerDTO expected = new OrderToCustomerDTO(order1.getId(), List.of(orderItemOnOrder.withId(orderItemId).withQuantity(1)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/remove/?id=" + order1.getId();
        //WHEN
        ResponseEntity<OrderToCustomerDTO> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItemToTakeOffOrder), headers), OrderToCustomerDTO.class);
        ResponseEntity<Product> returnedProduct = restTemplate.exchange("/api/products/" + product.getId(), HttpMethod.GET, new HttpEntity<>(headers), Product.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()), is(expected));
        assertThat(Objects.requireNonNull(returnedProduct.getBody()).getAmountInStock(), is(2));
        assertTrue(orderItemRepo.findById(orderItemId).isPresent());
    }

    @Test
    void takeOffOneOfSeveralItems() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        Product product2 = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        Product product3 = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItemDTO orderItemOnOrder = sampleOrderItemDTONoId().withProduct(mapProductWithDetails(product));
        OrderItemDTO orderItemOnOrder2 = sampleOrderItemDTONoId().withProduct(mapProductWithDetails(product2));
        OrderItemDTO orderItemOnOrder3 = sampleOrderItemDTONoId().withProduct(mapProductWithDetails(product3));
        OrderItem orderItemToTakeOffOrder = sampleOrderItemNoId().withProduct(product2);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(mapOrderItem(orderItemOnOrder), mapOrderItem(orderItemOnOrder2), mapOrderItem(orderItemOnOrder3)), OPEN));
        Long orderItemId = order1.getOrderItems().get(0).getId();
        Long orderItemId2 = order1.getOrderItems().get(1).getId();
        Long orderItemId3 = order1.getOrderItems().get(2).getId();
        OrderToCustomerDTO expected = new OrderToCustomerDTO(order1.getId(), List.of(orderItemOnOrder, orderItemOnOrder3));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/remove/?id=" + order1.getId();
        //WHEN
        ResponseEntity<OrderToCustomerDTO> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItemToTakeOffOrder), headers), OrderToCustomerDTO.class);
        ResponseEntity<Product> returnedProduct = restTemplate.exchange("/api/products/" + product.getId(), HttpMethod.GET, new HttpEntity<>(headers), Product.class);
        ResponseEntity<Product> returnedProduct2 = restTemplate.exchange("/api/products/" + product2.getId(), HttpMethod.GET, new HttpEntity<>(headers), Product.class);
        ResponseEntity<Product> returnedProduct3 = restTemplate.exchange("/api/products/" + product3.getId(), HttpMethod.GET, new HttpEntity<>(headers), Product.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()), is(expected));
        assertThat(response.getBody().getOrderItems().get(0).getQuantity(), is(1));
        assertThat(response.getBody().getOrderItems().get(1).getQuantity(), is(1));
        assertThat(Objects.requireNonNull(returnedProduct.getBody()).getAmountInStock(), is(1));
        assertThat(Objects.requireNonNull(returnedProduct2.getBody()).getAmountInStock(), is(2));
        assertThat(Objects.requireNonNull(returnedProduct3.getBody()).getAmountInStock(), is(1));
        assertTrue(orderItemRepo.findById(orderItemId).isPresent());
        assertTrue(orderItemRepo.findById(orderItemId2).isEmpty());
        assertTrue(orderItemRepo.findById(orderItemId3).isPresent());
    }

    @Test
    void reduceAmountOfOneOfSeveralItems() {
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        Product product2 = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        Product product3 = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItemDTO orderItemOnOrder = sampleOrderItemDTONoId().withProduct(mapProductWithDetails(product));
        OrderItemDTO orderItemOnOrder2 = sampleOrderItemDTONoId().withProduct(mapProductWithDetails(product2)).withQuantity(2);
        OrderItemDTO orderItemOnOrder3 = sampleOrderItemDTONoId().withProduct(mapProductWithDetails(product3));
        OrderItem orderItemToTakeOffOrder = sampleOrderItemNoId().withProduct(product2);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(mapOrderItem(orderItemOnOrder), mapOrderItem(orderItemOnOrder2), mapOrderItem(orderItemOnOrder3)), OPEN));
        Long orderItemId = order1.getOrderItems().get(0).getId();
        Long orderItemId2 = order1.getOrderItems().get(1).getId();
        Long orderItemId3 = order1.getOrderItems().get(2).getId();
        OrderToCustomerDTO expected = new OrderToCustomerDTO(order1.getId(), List.of(orderItemOnOrder, orderItemOnOrder2, orderItemOnOrder3));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/remove/?id=" + order1.getId();
        //WHEN
        ResponseEntity<OrderToCustomerDTO> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItemToTakeOffOrder), headers), OrderToCustomerDTO.class);
        ResponseEntity<Product> returnedProduct = restTemplate.exchange("/api/products/" + product.getId(), HttpMethod.GET, new HttpEntity<>(headers), Product.class);
        ResponseEntity<Product> returnedProduct2 = restTemplate.exchange("/api/products/" + product2.getId(), HttpMethod.GET, new HttpEntity<>(headers), Product.class);
        ResponseEntity<Product> returnedProduct3 = restTemplate.exchange("/api/products/" + product3.getId(), HttpMethod.GET, new HttpEntity<>(headers), Product.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()), is(expected));
        assertThat(response.getBody().getOrderItems().get(0).getQuantity(), is(1));
        assertThat(response.getBody().getOrderItems().get(1).getQuantity(), is(1));
        assertThat(response.getBody().getOrderItems().get(2).getQuantity(), is(1));
        assertThat(Objects.requireNonNull(returnedProduct.getBody()).getAmountInStock(), is(1));
        assertThat(Objects.requireNonNull(returnedProduct2.getBody()).getAmountInStock(), is(2));
        assertThat(Objects.requireNonNull(returnedProduct3.getBody()).getAmountInStock(), is(1));
        assertTrue(orderItemRepo.findById(orderItemId).isPresent());
        assertTrue(orderItemRepo.findById(orderItemId2).isPresent());
        assertTrue(orderItemRepo.findById(orderItemId3).isPresent());
    }

    @Test
    void removeItemsFromOrderFailsWhenOrderNonExistent(){
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItemDTO orderItemOnOrder = sampleOrderItemDTONoId().withProduct(mapProductWithDetails(product));
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(mapOrderItem(orderItemOnOrder)), OPEN));
        String expectedErrorMessage =  "You're trying to remove from an order that doesn't exist";
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/remove/?id=" + order1.getId() +  1;
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(orderItemOnOrder, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(expectedErrorMessage));
    }
    @Test
    void removeItemsFromOrderFailsWhenOrderAlreadyPaid(){
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItemDTO orderItemOnOrder = sampleOrderItemDTONoId().withProduct(mapProductWithDetails(product));
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(mapOrderItem(orderItemOnOrder)), PAID));
        String expectedErrorMessage =  "This order has already been cashed out!";
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/remove/?id=" + order1.getId();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(orderItemOnOrder, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(expectedErrorMessage));
    }
    @Test
    void removeItemsFromOrderFailsWhenItemNotOnOrder(){
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItemDTO orderItemOnOrder = sampleOrderItemDTONoId().withProduct(mapProductWithDetails(product));
        OrderItemDTO orderItemNotOnOrder = sampleOrderItemDTONoId().withProduct(sampleProductDTOWithDetailsWithId());
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(mapOrderItem(orderItemOnOrder)), OPEN));
        String expectedErrorMessage =  "The item you're trying to remove is not on the order";
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/remove/?id=" + order1.getId();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(orderItemNotOnOrder, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(expectedErrorMessage));
    }

    @Test
    void removeItemsFromOrderFailsWhenLessItemsOnOrderThanTryingToReduce(){
        //GIVEN
        Supplier sampleSupplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(sampleSupplier)).withAmountInStock(1));
        OrderItemDTO orderItemOnOrder = sampleOrderItemDTONoId().withProduct(mapProductWithDetails(product)).withQuantity(1);
        OrderItem orderItemToTakeOffOrder = sampleOrderItemNoId().withProduct(product).withQuantity(2);
        OrderToCustomer order1 = orderRepo.save(new OrderToCustomer(List.of(mapOrderItem(orderItemOnOrder)), OPEN));
        String expectedErrorMessage =  "It's not possible to remove more items than are on the order";
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        String URL = BASEURL + "/remove/?id=" + order1.getId();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(mapOrderItem(orderItemToTakeOffOrder), headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(expectedErrorMessage));
    }
}
