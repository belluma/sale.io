package capstone.backend.security.controller;

import capstone.backend.security.repository.UserRepository;
import capstone.backend.security.service.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;
    private RestTemplate restTemplateMock = mock(RestTemplate.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    private final UserMapper mapper = new UserMapper();

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Test
    void login() {
    }

    @Test
    void signup() {
    }
}
