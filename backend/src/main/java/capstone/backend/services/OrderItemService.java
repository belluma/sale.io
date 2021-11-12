package capstone.backend.services;

import capstone.backend.exception.model.EntityNotFoundException;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.repo.OrderQuantityRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderQuantityService {

    private final OrderQuantityRepo repo;


    public OrderQuantityService(OrderQuantityRepo repo) {
        this.repo = repo;
    }

    public List<OrderItem> getAllOrderQuantities() {
        return repo.findAll();
    }

    public OrderItem getSingleOrderQuantity(Long id) throws EntityNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Something went wrong while retrieving your data!"));
    }
    public OrderItem addOrderQuantity(OrderItem orderQuantity) {
        return repo.save(orderQuantity);
    }

}
