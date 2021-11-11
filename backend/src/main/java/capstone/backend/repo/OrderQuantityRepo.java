package capstone.backend.repo;

import capstone.backend.model.db.order.OrderQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderQuantityRepo extends JpaRepository<OrderQuantity, Long> {
}
