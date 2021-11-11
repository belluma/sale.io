package capstone.backend.repo;

import capstone.backend.model.db.order.OrderToSupplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderToSupplierRepo extends JpaRepository<OrderToSupplier, Long> {
}
