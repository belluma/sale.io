package capstone.backend.security.controller;

import capstone.backend.security.model.AppUser;
import capstone.backend.security.model.UserDTO;
import capstone.backend.security.repository.UserRepository;
import capstone.backend.security.service.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;
    private final RestTemplate restTemplateMock = mock(RestTemplate.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    private final UserMapper mapper = new UserMapper();

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
        userRepository.deleteAll();
    }

    @Test
    void login() {
        UserDTO user = sampleUser();
        userRepository.save(userToSaveInRepo(user));
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        Claims body = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(response.getBody())
                .getBody();
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(body.getSubject(), equalTo("username"));
    }

    @Test
    void loginFailsWithWrongPassword() {
        UserDTO user = sampleUser();
        userRepository.save(userToSaveInRepo(user));
        user.setPassword("123");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }
    @Test
    void loginFailsWithWrongUsername() {
        UserDTO user = sampleUser();
        user.setPassword("1234");
        user.setUsername("wrong_username");
        userRepository.save(mapper.mapUser(user));
        user.setPassword("123");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    void signupSavesUserAndReturnsLogin() {
        UserDTO user = sampleUser();
        AppUser savedUser = userToSaveInRepo(user);
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/signup", user, String.class);
        Claims body = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(response.getBody())
                .getBody();
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(body.getSubject(), equalTo("username"));
        assertThat(userRepository.findById(user.getUsername()), is(Optional.of(savedUser)));
    }
    @Test
    void signupFailsWhenUsernameAlreadyRegistered(){
        UserDTO user = sampleUser();
        userRepository.save(mapper.mapUser(user));
        user.setPassword("1234");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/signup", user, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_ACCEPTABLE));
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSignupFailsWhenLackingInformation")
    void signupFailsWhenLackingInformation(UserDTO user){
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/signup", user, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_ACCEPTABLE));
        assertThat(userRepository.findById(user.getUsername()), is(Optional.empty()));
    }

    private static Stream<Arguments> provideArgumentsForSignupFailsWhenLackingInformation(){
        return Stream.of(
                Arguments.of(new UserDTO("", "234")),
                Arguments.of(new UserDTO("username", ""))
        );
    }

    private UserDTO sampleUser() {
        return UserDTO
                .builder()
                .username("username")
                .password("password")
                .build();
    }

    private AppUser userToSaveInRepo(UserDTO user) {
        AppUser userToSave = mapper.mapUser(user);
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        return userToSave;
    }
}
