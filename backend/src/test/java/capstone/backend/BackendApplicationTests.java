package capstone.backend;

import capstone.backend.security.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootTest(classes = EmployeeRepository.class)
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
