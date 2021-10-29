package capstone.backend.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudRepository<DBO extends DatabaseObject, Long> extends JpaRepository<DBO , Long> {
}
