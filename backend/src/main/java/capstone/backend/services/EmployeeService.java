package capstone.backend.services;

import capstone.backend.mapper.EmployeeMapper;
import capstone.backend.repo.EmployeeRepo;
import capstone.backend.security.model.EmployeeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepo repo;
    private final EmployeeMapper mapper;

    public EmployeeService(EmployeeRepo repo, EmployeeMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return repo.findAll()
                .stream()
                .map(mapper::mapEmployeeAndConcealData)
                .toList();
    }
}
