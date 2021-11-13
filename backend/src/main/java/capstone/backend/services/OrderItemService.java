package capstone.backend.services;

import capstone.backend.exception.model.EntityNotFoundException;
import capstone.backend.mapper.OrderItemMapper;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.repo.OrderItemRepo;
import org.springframework.stereotype.Service;

import java.util.List;

import static capstone.backend.mapper.OrderItemMapper.mapOrderItem;

@Service
public class OrderItemService {

    private final OrderItemRepo repo;


    public OrderItemService(OrderItemRepo repo) {
        this.repo = repo;
    }

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

}
