package capstone.backend.services;

import capstone.backend.mapper.OrderItemMapper;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.model.dto.order.OrderToCustomerDTO;
import capstone.backend.repo.OrderItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

    public OrderItemDTO addItemToOrderOrUpdateQuantity(OrderItemDTO itemToAdd, OrderToCustomerDTO order) {
        AtomicReference<OrderItemDTO> orderItem = new AtomicReference<>();
        itemAlreadyOnOrder(itemToAdd, order).ifPresentOrElse(
                itemOnOrder -> orderItem.set(mapOrderItem(repo.save(mapOrderItem(itemOnOrder.withQuantity(itemOnOrder.getQuantity() + itemToAdd.getQuantity()))))),
                () -> orderItem.set(mapOrderItem(repo.save(mapOrderItem(itemToAdd))))
        );
        return orderItem.get();
    }

    public Optional<OrderItemDTO> itemAlreadyOnOrder(OrderItemDTO itemToAdd, OrderToCustomerDTO order) {
        return order
                .getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getProduct().equals(itemToAdd.getProduct()))
                .findFirst();
    }

    public void deleteOrderItem(Long id) {
        repo.deleteById(id);
    }
}
