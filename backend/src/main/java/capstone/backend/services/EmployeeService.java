package capstone.backend.services;

import capstone.backend.mapper.EmployeeMapper;
import capstone.backend.repo.EmployeeRepo;
import capstone.backend.security.model.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo repo;
    private final EmployeeMapper mapper;

    public List<EmployeeDTO> getAllEmployees() {
        return repo.findAll()
                .stream()
                .map(mapper::mapEmployeeAndConcealData)
                .toList();
    }

    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        return new EmployeeDTO();
    }

    public void deleteEmployee(String username) {
    }
}
