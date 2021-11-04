package capstone.backend.controller;

import capstone.backend.mapper.ProductMapper;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.repo.ProductRepo;
import capstone.backend.security.model.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static capstone.backend.utils.EmployeeTestUtils.sampleUser;
import static capstone.backend.utils.ProductTestUtils.sampleProduct;
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
        repo.save(sampleProduct());
        ResponseEntity<ProductDTO[]> response = restTemplate.exchange("/api/product", ProductDTO[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(Arrays.asList(response.getBody()), List.of(mapper.mapProductWithDetails(sampleProduct())));
    }

    @Test
    void getProductDetails() {
        repo.save(sampleProduct());
        ResponseEntity<ProductDTO> response = restTemplate.exchange("/api/product", ProductDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(Arrays.asList(response.getBody()), List.of(mapper.mapProductWithDetails(sampleProduct())));
    }

    @Test
    void createProduct() {
    }

    @Test
    void editProduct() {
    }
}
