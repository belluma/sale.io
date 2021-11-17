package capstone.backend.controller;

import capstone.backend.mapper.ProductMapper;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.repo.ProductRepo;
import capstone.backend.repo.SupplierRepo;
import capstone.backend.utils.ControllerTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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

import static capstone.backend.mapper.ProductMapper.mapProductWithDetails;
import static capstone.backend.mapper.SupplierMapper.mapSupplier;
import static capstone.backend.utils.ProductTestUtils.*;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {


    @Autowired
    ProductController controller;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    ProductMapper mapper;
    @Autowired
    ControllerTestUtils utils;

    String BASEURL = "/api/products";

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
    void clearDB() {
        productRepo.deleteAll();
    }

    @Test
    void test() {
        assertTrue(container.isRunning());
    }


    @Test
    void getAllProductsWithDetails() {
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(List.of(supplier)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO[]> response = restTemplate.exchange(BASEURL, HttpMethod.GET, new HttpEntity<>(headers), ProductDTO[].class);
        ProductDTO expected = mapProductWithDetails(product);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(Arrays.asList(response.getBody()), List.of(expected));
    }

    @Test
    void getProductDetails() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(List.of(supplier)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL + "/" + product.getId(), HttpMethod.GET, new HttpEntity<>(headers), ProductDTO.class);
        ProductDTO expected = mapProductWithDetails(product);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
    }

    @Test
    void getProductDetailsReturnsNotFoundWhenProductNonExistent() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL + "/12", HttpMethod.GET, new HttpEntity<>(headers), ProductDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void createProduct() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        ProductDTO product = sampleProductDTOWithDetailsWithId();
        product.setSuppliers(List.of(mapSupplier(supplier)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(product, headers), ProductDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(productRepo.findAll().size(), is(1));
    }

    @Test
    void editProduct() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(List.of(supplier)));
        ProductDTO productToEdit = mapProductWithDetails(product);
        productToEdit.setRetailPrice(99.99F);
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL + "/" + product.getId(), HttpMethod.PUT, new HttpEntity<>(productToEdit, headers), ProductDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(productToEdit));
    }

    @Test
    void editProductFailsWhenProductNonExistant() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        ProductDTO productToEdit = sampleProductDTOWithDetailsWithId();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL + "/1", HttpMethod.PUT, new HttpEntity<>(productToEdit, headers), ProductDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

}
