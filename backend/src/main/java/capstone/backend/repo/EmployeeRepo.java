package capstone.backend.repo;

import capstone.backend.security.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    boolean existsByUsername(String username);

    Optional<Employee> findByUsername(String username);

    void removeByUsername(String username);
}
