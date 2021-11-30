package capstone.backend.controller;

import capstone.backend.CombinedTestContainer;
import capstone.backend.model.db.Category;
import capstone.backend.model.dto.CategoryDTO;
import capstone.backend.repo.CategoryRepo;
import capstone.backend.utils.ControllerTestUtils;
import org.junit.jupiter.api.AfterEach;
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
import java.util.Objects;

import static capstone.backend.mapper.CategoryMapper.mapCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

    @Autowired
    CategoryController controller;
    @Autowired
    CategoryRepo repo;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    ControllerTestUtils utils;

    String BASEURL = "/api/categories";

    @Container
    public static PostgreSQLContainer<CombinedTestContainer> container =CombinedTestContainer.getInstance();

    @AfterEach
    public void clearDB() {
        repo.deleteAll();
    }

    @Test
    void test() {
        assertTrue(container.isRunning());
    }

    @Test
    void getAllCategories() {
        //GIVEN
        repo.save(new Category(1L, "test category"));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<CategoryDTO[]> response = restTemplate.exchange(BASEURL, HttpMethod.GET, new HttpEntity<>(headers), CategoryDTO[].class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(Arrays.asList(Objects.requireNonNull(response.getBody())), List.of(new CategoryDTO(1L, "test category")));
    }

    @Test
    void createCategory() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        CategoryDTO expected = new CategoryDTO(1L, "category");
        //WHEN
        ResponseEntity<CategoryDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(expected, headers), CategoryDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()), is(expected.withId(response.getBody().getId())));
        assertThat(mapCategory(repo.findById(response.getBody().getId()).get()), is(expected.withId(response.getBody().getId())));
        assertThat(repo.findAll().size(), is(1));
    }
}
