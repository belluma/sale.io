package capstone.backend.security.service;

import capstone.backend.models.db.contact.Employee;
import capstone.backend.models.dto.contact.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee mapEmployee(EmployeeDTO employee) {
        return new Employee();
    }
}
