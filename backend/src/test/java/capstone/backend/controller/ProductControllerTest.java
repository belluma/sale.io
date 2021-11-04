package capstone.backend.controller;

import capstone.backend.mapper.ProductMapper;
import capstone.backend.model.db.Product;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.repo.ProductRepo;
import capstone.backend.utils.ControllerTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static capstone.backend.mapper.ProductMapper.mapProductWithDetails;
import static capstone.backend.utils.ProductTestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {


    @Autowired
    ProductController controller;
    @Autowired
    GlobalExceptionHandler exceptionHandler;
    @Autowired
    ProductRepo repo;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ProductMapper mapper;
    @Autowired
    ControllerTestUtils utils;

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
        repo.deleteAll();
    }

    @Test
    void test() {
        assertTrue(container.isRunning());
    }


    @Test
    void getAllProductsWithDetails() {
        Product product = repo.save(sampleProduct());
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        ResponseEntity<ProductDTO[]> response = restTemplate.exchange("/api/product", HttpMethod.GET, new HttpEntity<>(headers), ProductDTO[].class);
        ProductDTO expected = mapProductWithDetails(product);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(Arrays.asList(response.getBody()), List.of(expected));
    }

    @Test
    void getProductDetails() {
        Product product = repo.save(sampleProduct());
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        ResponseEntity<ProductDTO> response = restTemplate.exchange("/api/product/" + product.getId(), HttpMethod.GET, new HttpEntity<>(headers), ProductDTO.class);
        ProductDTO expected = mapProductWithDetails(product);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
    }

    @Test
    void getProductDetailsReturnsNotFoundWhenProductNonExistant(){
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        ResponseEntity<ProductDTO> response = restTemplate.exchange("/api/product/12", HttpMethod.GET, new HttpEntity<>(headers), ProductDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is("Couldn't find a product with the id 12"));
    }

    @Test
    void createProduct() {
        ProductDTO product = sampleProductDTOWithDetailsWithId();
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        ResponseEntity<ProductDTO> response = restTemplate.exchange("/api/product/", HttpMethod.POST, new HttpEntity<>(product, headers), ProductDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(repo.findAll().size(), is(1));
    }

    @Test
    void editProduct() {
        Product product = repo.save(sampleProduct());
        ProductDTO productToEdit = mapProductWithDetails(product);
        productToEdit.setRetailPrice(99.99F);
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        ResponseEntity<ProductDTO> response = restTemplate.exchange("/api/product/" + product.getId(), HttpMethod.PUT, new HttpEntity<>(productToEdit, headers), ProductDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getRetailPrice(), is(99.99F));
    }

    @Test
    void editProductFailsWhenProductNonExistant(){
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        ProductDTO productToEdit = sampleProductDTOWithDetailsWithId();
        ResponseEntity<ProductDTO> response = restTemplate.exchange("/api/product/1", HttpMethod.PUT, new HttpEntity<>(productToEdit,headers), ProductDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(String.format("Couldn't find a product with the id %d", productToEdit.getId())));

    }

}
