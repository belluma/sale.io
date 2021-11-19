package capstone.backend.services;

import capstone.backend.mapper.OrderToCustomerMapper;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.model.dto.order.OrderToCustomerDTO;
import capstone.backend.repo.OrderToCustomerRepo;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

import static capstone.backend.mapper.OrderItemMapper.mapOrderItem;
import static capstone.backend.mapper.OrderToCustomerMapper.mapOrder;
import static capstone.backend.model.enums.OrderStatus.OPEN;
import static capstone.backend.model.enums.OrderStatus.PAID;


@RequiredArgsConstructor
public class OrderToCustomerService {

    private final OrderToCustomerRepo repo;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    public List<OrderToCustomerDTO> getAllOrders() {
        return repo.findAll()
                .stream()
                .map(OrderToCustomerMapper::mapOrder)
                .toList();
    }

    public List<OrderToCustomerDTO> getAllOpenOrders() {
        return repo.findAllByStatus(OPEN)
                .stream()
                .map(OrderToCustomerMapper::mapOrder)
                .toList();
    }

    public OrderToCustomerDTO createEmptyOrder() {
        return mapOrder(repo.save(new OrderToCustomer(OPEN)));
    }

    public OrderToCustomerDTO addItemsToOrder(Long orderId, OrderItemDTO orderItem, OrderToCustomerDTO orderToCustomer) throws IllegalArgumentException {
        OrderToCustomer openOrder = validateOrder(orderId, orderItem);
        OrderItem orderItemWithUpdatedAmount = orderItemService.addQuantityToItemAlreadyOnOrder(mapOrderItem(orderItem));
        productService.substractStockWhenAddingItemToBill(mapOrderItem(orderItem));
        openOrder.setOrderItems(
                openOrder
                        .getOrderItems()
                        .stream()
                        .map(oldOrderItem -> Objects.equals(oldOrderItem.getId(), orderItemWithUpdatedAmount.getId()) ? orderItemWithUpdatedAmount : oldOrderItem)
                        .toList());
        return mapOrder(repo.save(openOrder));
    }

    public OrderToCustomerDTO cashoutOrder(OrderToCustomerDTO orderToCustomer) {
        OrderToCustomer openOrder = repo.findById(orderToCustomer.getId()).orElseThrow(EntityNotFoundException::new);
        if (orderAlreadyPaid(openOrder)) {
            throw new IllegalArgumentException("This order has already been cashed out!");
        }
        openOrder.setStatus(PAID);
        return mapOrder(repo.save(openOrder));
    }

    private OrderToCustomer validateOrder(Long orderId, OrderItemDTO orderItem) {
       if(!orderExists(orderId)){
           throw new EntityNotFoundException("You're trying to add to an order that doesn't exist");
       }
        if (orderAlreadyPaid(orderId)) {
            throw new IllegalArgumentException("This order has already been cashed out!");
        }
        if (!productService.productExists(orderItem.getProduct())) {
            throw new IllegalArgumentException("You're trying to add a product that doesn't exist");
        }
        return repo.findById(orderId).orElseThrow(EntityNotFoundException::new);
    }

    private boolean orderExists(OrderToCustomerDTO order) {
        return (order.getId() != null && repo.existsById(order.getId()));
    }

    private boolean orderExists(Long orderId) {
        return (orderId != null && repo.existsById(orderId));
    }

    private boolean orderAlreadyPaid(OrderToCustomerDTO order) {
        OrderToCustomer existingOrder = repo.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        return existingOrder.getStatus() == PAID;
    }

    private boolean orderAlreadyPaid(OrderToCustomer order) {
        OrderToCustomer existingOrder = repo.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        return existingOrder.getStatus() == PAID;
    }

    private boolean orderAlreadyPaid(Long orderId) {
        OrderToCustomer existingOrder = repo.findById(orderId).orElseThrow(EntityNotFoundException::new);
        return existingOrder.getStatus() == PAID;
    }

}
