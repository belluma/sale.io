package capstone.backend.services;

import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.mapper.EmployeeMapper;
import capstone.backend.repo.EmployeeRepo;
import capstone.backend.security.model.Employee;
import capstone.backend.security.model.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static capstone.backend.mapper.EmployeeMapper.mapAndConcealData;
import static capstone.backend.mapper.EmployeeMapper.mapEmployee;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo repo;
    private final EmployeeMapper mapper;
    private final Supplier<UUID> uuidSupplier = UUID::randomUUID;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<EmployeeDTO> getAllEmployees() {
        return repo.findAll()
                .stream()
                .map(mapper::mapEmployeeAndConcealData)
                .toList();
    }

    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        verifyUserData(employee);
        employee.setUsername(getUuid());
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return mapAndConcealData(repo.save(mapEmployee(employee)));
    }

    private void verifyUserData(EmployeeDTO employee) throws IllegalArgumentException, EntityWithThisIdAlreadyExistException {
        if (employee.getFirstName() == null && employee.getLastName() == null) {
            throw new IllegalArgumentException("At least one name attribute needs to be set!");
        }
        if (repo.existsById(employee.getId())) {
            throw new EntityWithThisIdAlreadyExistException(String.format("A user with id %d already exists", employee.getId()));
        }
    }

    private String getUuid() {
        return uuidSupplier.get().toString();
    }

    public void deleteEmployee(String username) throws EntityNotFoundException {
        if (!repo.existsByUsername(username)) {
            throw new EntityNotFoundException(String.format("User with username %s not found", username));
        }
        repo.deleteByUsername(username);
    }
}
