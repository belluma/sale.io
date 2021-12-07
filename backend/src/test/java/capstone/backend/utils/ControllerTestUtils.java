package capstone.backend.utils;

import capstone.backend.repo.EmployeeRepo;
import capstone.backend.security.model.Employee;
import capstone.backend.security.model.EmployeeDTO;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ControllerTestUtils {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmployeeRepo repo;
    private final TestRestTemplate restTemplate;

    public ControllerTestUtils(EmployeeRepo repo, TestRestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    public HttpHeaders createHeadersWithJwtAuth() {
        repo.deleteAll();
        Employee user = new Employee("user", passwordEncoder.encode("1234"));
        repo.save(user);
        EmployeeDTO loginData = new EmployeeDTO("user", "1234");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", loginData, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(Objects.requireNonNull(response.getBody()));
        return headers;
    }
}
