package capstone.backend.services;

import capstone.backend.security.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    public List<Employee> getAllEmployees() {
        return List.of();
    }
}
