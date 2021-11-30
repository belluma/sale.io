package capstone.backend.controller;

import capstone.backend.CombinedTestContainer;
import capstone.backend.exception.CustomError;
import capstone.backend.exception.GlobalExceptionHandler;
import capstone.backend.mapper.SupplierMapper;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.repo.SupplierRepo;
import capstone.backend.utils.ControllerTestUtils;
import org.junit.jupiter.api.AfterEach;
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

import static capstone.backend.mapper.SupplierMapper.mapSupplier;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplierDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.is;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SupplierControllerTest {

    @Autowired
    SupplierController controller;
    @Autowired
    GlobalExceptionHandler exceptionHandler;
    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SupplierMapper mapper;
    @Autowired
    ControllerTestUtils utils;
    String BASEURL = "/api/suppliers";

    @Container
    public static PostgreSQLContainer<CombinedTestContainer> container = CombinedTestContainer.getInstance();

    @AfterEach
    public void clearDB() {
        supplierRepo.deleteAll();
    }

    @Test
    void containerIsRunning() {
        assertTrue(container.isRunning());
    }

    @Test
    void getAllSuppliersWithDetails() {
        Supplier supplier = supplierRepo.save(sampleSupplier());
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<SupplierDTO[]> response = restTemplate.exchange(BASEURL, HttpMethod.GET, new HttpEntity<>(headers), SupplierDTO[].class);
        SupplierDTO expected = mapSupplier(supplier);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(Arrays.asList(Objects.requireNonNull(response.getBody())), List.of(expected));
    }

    @Test
    void getSupplierDetails() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<SupplierDTO> response = restTemplate.exchange(BASEURL + "/" + supplier.getId(), HttpMethod.GET, new HttpEntity<>(headers), SupplierDTO.class);
        SupplierDTO expected = mapSupplier(supplier);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
    }

    @Test
    void getSupplierDetailsReturnsNotFoundWhenSupplierNonExistent() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL + "/12", HttpMethod.GET, new HttpEntity<>(headers), CustomError.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(String.format("Couldn't find a supplier with the id %d", 12)));
    }

    @Test
    void createSupplier() {
        //GIVEN
        SupplierDTO supplier = sampleSupplierDTO();
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<SupplierDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(supplier, headers), SupplierDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(supplierRepo.findAll().size(), is(1));
    }
    @Test
    void createSupplierFailsWhenSupplierAlreadyExistant() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        SupplierDTO supplier = mapSupplier(supplierRepo.save(sampleSupplier()));
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(supplier, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is((String.format("Supplier %s already has the id %d", supplier.getFirstName(), supplier.getId()))));
    }

    @Test
    void editSupplier() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        SupplierDTO supplierToEdit = mapSupplier(supplier);
//        supplierToEdit.setOrderDay(Weekdays.FRIDAY);
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<SupplierDTO> response = restTemplate.exchange(BASEURL + "/" + supplier.getId(), HttpMethod.PUT, new HttpEntity<>(supplierToEdit, headers), SupplierDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(supplierToEdit));
    }

    @Test
    void editSupplierFailsWhenSupplierNonExistant() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        SupplierDTO supplierToEdit = sampleSupplierDTO();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL + "/1", HttpMethod.PUT, new HttpEntity<>(supplierToEdit, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(String.format("Couldn't find a supplier with the id %d", supplierToEdit.getId())));
    }

}
