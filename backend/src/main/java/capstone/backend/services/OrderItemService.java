package capstone.backend.services;

import capstone.backend.mapper.OrderItemMapper;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.repo.OrderItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static capstone.backend.mapper.OrderItemMapper.mapOrderItem;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepo repo;

    public List<OrderItemDTO> getAllOrderItems() {

        return repo.findAll()
                .stream()
                .map(OrderItemMapper::mapOrderItem)
                .toList();
    }

    public OrderItemDTO getSingleOrderItem(Long id) throws EntityNotFoundException {
        return repo.findById(id)
                .map(OrderItemMapper::mapOrderItem)
                .orElseThrow(() -> new EntityNotFoundException("Something went wrong while retrieving your data!"));
    }
    public OrderItemDTO addOrderItem(OrderItemDTO orderItem) {
        return mapOrderItem(repo.save(mapOrderItem(orderItem)));
    }


    public OrderItem addItemsToExistingOrder(OrderItem orderItem) {
        return new OrderItem();
    }
}
