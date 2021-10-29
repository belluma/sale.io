package capstone.backend.repository;

import capstone.backend.models.db.OrderToSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderToSupplierRepo extends JpaRepository <OrderToSupplier,Long>{
}
