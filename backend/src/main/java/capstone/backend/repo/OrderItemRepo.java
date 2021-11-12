package capstone.backend.repo;

import capstone.backend.model.db.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderQuantityRepo extends JpaRepository<OrderItem, Long> {
}
