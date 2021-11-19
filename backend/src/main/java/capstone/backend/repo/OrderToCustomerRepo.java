package capstone.backend.repo;

import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderToCustomerRepo extends JpaRepository<OrderToCustomer, Long> {
    List<OrderToCustomer> findAllByStatus(OrderStatus status);
}
