package capstone.backend.controller;

import capstone.backend.CombinedTestContainer;
import capstone.backend.exception.CustomError;
import capstone.backend.exception.GlobalExceptionHandler;
import capstone.backend.mapper.EmployeeMapper;
import capstone.backend.repo.EmployeeRepo;
import capstone.backend.security.model.Employee;
import capstone.backend.security.model.EmployeeDTO;
import capstone.backend.utils.ControllerTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static capstone.backend.mapper.EmployeeMapper.mapAndConcealData;
import static capstone.backend.mapper.EmployeeMapper.mapEmployee;
import static capstone.backend.utils.EmployeeTestUtils.sampleUser;
import static capstone.backend.utils.EmployeeTestUtils.sampleUserDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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
    @Autowired
    ControllerTestUtils utils;

    final String BASEURL = "/api/employees";

    @Container
    public static PostgreSQLContainer<CombinedTestContainer> container = CombinedTestContainer.getInstance();

    @BeforeEach
    @AfterEach
    public void clearDB() {
        repo.deleteAll();
    }

    @Test
    void test() {
        assertTrue(container.isRunning());
    }


    @Test
    void getAllEmployees() {
        //GIVEN
        repo.save(sampleUser());
        //WHEN
        ResponseEntity<EmployeeDTO[]> response = restTemplate.getForEntity(BASEURL, EmployeeDTO[].class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(Arrays.asList(Objects.requireNonNull(response.getBody())), List.of(mapper.mapEmployeeAndConcealData(sampleUser())));
    }

    @Test
    void createEmployee() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        EmployeeDTO userToSave = sampleUserDTO();
        EmployeeDTO expected = mapAndConcealData(mapEmployee(userToSave));
        //WHEN
        ResponseEntity<EmployeeDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(userToSave, headers), EmployeeDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()).getPassword(), is(nullValue()));
        assertThat(response.getBody().getRoles(), is(expected.getRoles()));
        assertThat(repo.findAll().size(), is(2));
    }

    @Test
    void createEmployeeFailsWithoutCredentials() {
        //GIVEN
        EmployeeDTO userToSave = sampleUserDTO();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(userToSave), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        assertThat(repo.findAll().size(), is(0));
    }

    @Test
    void createEmployeeFailsWithoutName() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        EmployeeDTO userToSave = sampleUserDTO();
        userToSave.setFirstName(null);
        userToSave.setLastName(null);
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(userToSave, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is("At least one name attribute needs to be set!"));
        assertThat(repo.findAll().size(), is(1));
    }

    @Test
    void createEmployeeFailsWithNoPassword() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        EmployeeDTO userToSave = sampleUserDTO();
        userToSave.setPassword(null);
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(userToSave, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is("Cannot save employee without password"));
        assertThat(repo.findAll().size(), is(1));
    }

    @Test
    void deleteEmployee() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        Employee savedUser = repo.save(sampleUser());
        //WHEN
        ResponseEntity<Void> response = restTemplate.exchange(BASEURL + "/" + savedUser.getUsername(), HttpMethod.DELETE, new HttpEntity<>( headers), Void.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(repo.findAll().size(), is(1));
    }
}
