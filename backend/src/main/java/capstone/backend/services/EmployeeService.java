package capstone.backend.services;

import capstone.backend.repo.EmployeeRepo;
import capstone.backend.security.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepo repo;

    public EmployeeService(EmployeeRepo repo) {
        this.repo = repo;
    }

    public List<Employee> getAllEmployees() {

        return repo.findAll()
                .stream()
                .map();
    }
}
