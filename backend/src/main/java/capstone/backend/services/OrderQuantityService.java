package capstone.backend.services;

import capstone.backend.exception.model.EntityNotFoundException;
import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.Order;
import capstone.backend.model.db.order.OrderQuantity;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.repo.OrderQuantityRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderQuantityService {

    private final OrderQuantityRepo repo;


    public OrderQuantityService(OrderQuantityRepo repo) {
        this.repo = repo;
    }

    public List<OrderQuantity> getAllOrderQuantities() {
        return repo.findAll();
    }

    public OrderQuantity getSingleOrderQuantity(Long id) throws EntityNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Something went wrong while retrieving your data!"));
    }
    public OrderQuantity addOrderQuantity(Product product, OrderToSupplier order, int quantity) {
        return repo.save(
                OrderQuantity
                        .builder()
                        .product(product)
                        .order(order)
                        .quantity(quantity)
                        .build()
        );
    }
    
}
