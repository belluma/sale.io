package capstone.backend.repo;

import capstone.backend.security.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    public boolean existsByUsername(String username);
}
