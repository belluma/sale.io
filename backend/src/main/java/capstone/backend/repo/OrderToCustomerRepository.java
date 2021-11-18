package capstone.backend.repo;

import capstone.backend.model.db.order.OrderToCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderToCustomerRepository extends JpaRepository<OrderToCustomer, Long> {

}
