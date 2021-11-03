package capstone.backend.controller;

import capstone.backend.mapper.EmployeeMapper;
import capstone.backend.repo.EmployeeRepo;
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

import static capstone.backend.utils.TestUtils.sampleUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {

    @Autowired
    EmployeeController controller;
    @Autowired
    GlobalExceptionHandler exceptionHandler;
    @Autowired
    EmployeeRepo repo;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmployeeMapper mapper;

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withDatabaseName("quiz")
            .withUsername("quiz")
            .withPassword("quiz");

    @DynamicPropertySource //https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/
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
    void getAllEmployees(){
        repo.save(sampleUser());
        ResponseEntity<EmployeeDTO[]> response = restTemplate.getForEntity("/api/employee", EmployeeDTO[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(Arrays.asList(response.getBody()), List.of(mapper.mapEmployeeAndConcealData(sampleUser())));
    }
}
