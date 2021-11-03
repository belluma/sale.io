package capstone.backend.security.repository;

import capstone.backend.security.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByUsername(String username);
}
