package capstone.backend.services;

import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.mapper.OrderToCustomerMapper;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.order.OrderToCustomerDTO;
import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.repo.OrderToCustomerRepo;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static capstone.backend.mapper.OrderToCustomerMapper.mapOrder;
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

    public OrderToCustomerDTO createEmptyOrder() {
        return mapOrder(repo.save(new OrderToCustomer()));
    }

    public OrderToCustomerDTO addItemsToOrder(Long orderId, OrderItem orderItem) throws EntityNotFoundException {
        OrderToCustomer openOrder = repo.findById(orderId).orElseThrow(EntityNotFoundException::new);
        if(orderAlreadyPaid(openOrder)){
            throw new IllegalArgumentException("This order has already been cashed out!");
        }
        OrderItem orderItemWithUpdatedAmount = orderItemService.addItemsToExistingOrder(orderItem);
        openOrder.setOrderItems(
                openOrder
                        .getOrderItems()
                        .stream()
                        .map(oldOrderItem -> oldOrderItem.getId() == orderItemWithUpdatedAmount.getId() ? orderItemWithUpdatedAmount : oldOrderItem)
                        .toList());
        return mapOrder(repo.save(openOrder));
    }

    public OrderToCustomerDTO cashoutOrder(OrderToCustomerDTO orderToCustomer){
        OrderToCustomer openOrder = repo.findById(orderToCustomer.getId()).orElseThrow(EntityNotFoundException::new);
        if(orderAlreadyPaid(openOrder)){
            throw new IllegalArgumentException("This order has already been cashed out!");
        }
        openOrder.setStatus(PAID);
        return mapOrder(repo.save(openOrder));
    }

    private boolean orderExists(OrderToCustomerDTO order) {
        return (order.getId() != null && repo.existsById(order.getId()));
    }
    private boolean orderAlreadyPaid(OrderToCustomerDTO order){
        OrderToCustomer existingOrder = repo.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        return existingOrder.getStatus() == PAID;
    }
  private boolean orderAlreadyPaid(OrderToCustomer order){
        OrderToCustomer existingOrder = repo.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        return existingOrder.getStatus() == PAID;
    }

}
