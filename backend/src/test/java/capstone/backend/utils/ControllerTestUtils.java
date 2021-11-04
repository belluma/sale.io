package capstone.backend.utils;

import capstone.backend.security.model.Employee;
import capstone.backend.security.model.EmployeeDTO;
import capstone.backend.security.repository.EmployeeRepository;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ControllerTestUtils {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmployeeRepository repo;
    private final TestRestTemplate restTemplate;

    public ControllerTestUtils(EmployeeRepository repo, TestRestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    public HttpHeaders createHeadersWithJwtAuth() {

        Employee user = new Employee("user", passwordEncoder.encode("1234"));
        repo.save(user);
        EmployeeDTO loginData = new EmployeeDTO("user", "1234");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", loginData, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(response.getBody());
        return headers;
    }
    }
