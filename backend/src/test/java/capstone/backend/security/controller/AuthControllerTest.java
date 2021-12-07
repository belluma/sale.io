package capstone.backend.security.controller;

import capstone.backend.mapper.EmployeeMapper;
import capstone.backend.repo.EmployeeRepo;
import capstone.backend.security.model.EmployeeDTO;
import capstone.backend.utils.EmployeeTestUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private EmployeeRepo repo;
    private final EmployeeTestUtils utils = new EmployeeTestUtils();

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withDatabaseName("quiz")
            .withUsername("quiz")
            .withPassword("quiz");

    @AfterEach
    public void clearDb() {
        repo.deleteAll();
    }

    @Test
    void login() {
        //GIVEN
        EmployeeDTO user = EmployeeTestUtils.sampleUserDTO();
        repo.save(utils.userWithEncodedPassword(user));
        //WHEN
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        Claims body = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(response.getBody())
                .getBody();
        //THEN
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(body.getSubject(), equalTo("username"));
    }

    @Test
    void loginFailsWithWrongPassword() {
        //GIVEN
        EmployeeDTO user = EmployeeTestUtils.sampleUserDTO();
        repo.save(utils.userWithEncodedPassword(user));
        user.setPassword("123");
        //WHEN
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    void loginFailsWithWrongUsername() {
        //GIVEN
        EmployeeDTO user = EmployeeTestUtils.sampleUserDTO();
        user.setPassword("1234");
        user.setUsername("wrong_username");
        repo.save(EmployeeMapper.mapEmployee(user));
        user.setPassword("123");
        //WHEN
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    void signupSavesUserAndReturnsLogin() {
        //GIVEN
        EmployeeDTO user = EmployeeTestUtils.sampleUserDTO();
        //WHEN
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/signup", user, String.class);
        //THEN
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(repo.findAll().size(), is(1));
    }

    @Test
    void signupFailsWhenUsernameAlreadyRegistered() {
        //GIVEN
        EmployeeDTO user = EmployeeTestUtils.sampleUserDTO();
        repo.save(EmployeeMapper.mapEmployee(user));
        user.setPassword("1234");
        //WHEN
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/signup", user, String.class);
        //THEN
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    void signupFailsWhenInvalidPassword() {
        //GIVEN
        EmployeeDTO user = new EmployeeDTO("username", "");
        //WHEN
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/signup", user, String.class);
        //THEN
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertThat(repo.findAll().size(), is(0));
    }


}
