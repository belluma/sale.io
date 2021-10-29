package capstone.backend.security.service;

import capstone.backend.models.db.Employee;
import capstone.backend.models.dto.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee mapEmployee(EmployeeDTO employee) {
        return new Employee();
    }
}
