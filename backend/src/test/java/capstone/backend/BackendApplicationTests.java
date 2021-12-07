package capstone.backend;
import capstone.backend.repo.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootTest(classes = EmployeeRepo.class)
class BackendApplicationTests {

    @Test
    void contextLoads() {
        assertThat(true, is(true));
    }

}
