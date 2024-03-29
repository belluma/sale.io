package capstone.backend.services;

import capstone.backend.mapper.OrderToCustomerMapper;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.db.order.OrderToCustomer;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.model.dto.order.OrderToCustomerDTO;
import capstone.backend.repo.OrderToCustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static capstone.backend.mapper.OrderItemMapper.mapOrderItem;
import static capstone.backend.mapper.OrderToCustomerMapper.mapOrder;
import static capstone.backend.model.enums.OrderToCustomerStatus.OPEN;
import static capstone.backend.model.enums.OrderToCustomerStatus.PAID;


@RequiredArgsConstructor
@Service
public class OrderToCustomerService {

    private final OrderToCustomerRepo repo;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private static final String BEEN_CASHED_OUT = "This order has already been cashed out!";

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

    public OrderToCustomerDTO getSpecificOrder(Long id) {
        return repo.findById(id)
                .map(OrderToCustomerMapper::mapOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %d not found", id)));
    }


    public OrderToCustomerDTO createEmptyOrder() {
        return mapOrder(repo.save(new OrderToCustomer(OPEN)));
    }

    public OrderToCustomerDTO addItemsToOrder(Long orderId, OrderItemDTO orderItem) throws IllegalArgumentException {
        OrderToCustomer openOrder = validateOrderWhenAddItems(orderId, orderItem);
        productService.substractStockWhenAddingItemToBill(mapOrderItem(orderItem));
        updateAmountOnBill(openOrder, mapOrderItem(orderItem));
        return mapOrder(repo.save(openOrder));
    }

    private void updateAmountOnBill(OrderToCustomer order, OrderItem orderItem) {
        List<OrderItem> itemsOnBill = new ArrayList<>(order.getOrderItems());
        int i = itemsOnBill.indexOf(orderItem);
        if (i < 0) {
            itemsOnBill.add(orderItem);
        } else {
            itemsOnBill.get(i).setQuantity(itemsOnBill.get(i).getQuantity() + orderItem.getQuantity());
        }
        order.setOrderItems(itemsOnBill);
    }

    private OrderToCustomer validateOrderWhenAddItems(Long orderId, OrderItemDTO orderItem) {
        throwWhenOrderDoesNotExist(orderId, "add to");
        if (orderAlreadyPaid(orderId)) {
            throw new IllegalArgumentException(BEEN_CASHED_OUT);
        }
        if (!productService.productExists(orderItem.getProduct())) {
            throw new IllegalArgumentException("You're trying to add a product that doesn't exist");
        }
        if (!enoughItemsInStock(orderItem)) {
            throw new IllegalArgumentException("Not enough items in stock!");
        }
        return repo.findById(orderId).orElseThrow(EntityNotFoundException::new);
    }

    public OrderToCustomerDTO removeItemsFromOrder(Long orderId, OrderItemDTO orderItem) throws IllegalArgumentException, EntityNotFoundException {
        OrderToCustomer openOrder = validateOrderWhenRemoveItems(orderId, orderItem);
        reduceAmountOrTakeOffBillIfZero(openOrder, mapOrderItem(orderItem));
        productService.resetAmountInStockWhenRemovingFromBill(mapOrderItem(orderItem));
        return mapOrder(repo.save(openOrder));
    }

    private OrderToCustomer validateOrderWhenRemoveItems(Long orderId, OrderItemDTO orderItem) {
        throwWhenOrderDoesNotExist(orderId, "remove from");
        if (orderAlreadyPaid(orderId)) {
            throw new IllegalArgumentException(BEEN_CASHED_OUT);
        }
        if (itemNotOnOrder(orderId, orderItem)) {
            throw new IllegalArgumentException("The item you're trying to remove is not on the order");
        }
             orderHasLessItemsThanTryingToReduce(orderItem, orderId);
        return repo.findById(orderId).orElseThrow(EntityNotFoundException::new);
    }

    private boolean itemNotOnOrder(Long orderId, OrderItemDTO orderItem) {
        return orderItemService.itemAlreadyOnOrder(orderItem, getSpecificOrder(orderId)).isEmpty();
    }

    private void reduceAmountOrTakeOffBillIfZero(OrderToCustomer order, OrderItem orderItem) {
        List<OrderItem> itemsOnBill = new ArrayList<>(order.getOrderItems());
        int i = itemsOnBill.indexOf(orderItem);
        boolean delete = false;
        Long orderItemId = itemsOnBill.get(i).getId();
        int newQty = itemsOnBill.get(i).getQuantity() - orderItem.getQuantity();
        if (newQty > 0) {
            itemsOnBill.get(i).setQuantity(newQty);
        } else {
            delete = true;
            itemsOnBill.remove(i);
        }
        order.setOrderItems(itemsOnBill);
        if (delete) {
            orderItemService.deleteOrderItem(orderItemId);
        }
    }

    public OrderToCustomerDTO cashoutOrder(OrderToCustomerDTO orderToCustomer) {
        OrderToCustomer openOrder = repo.findById(orderToCustomer.getId()).orElseThrow(EntityNotFoundException::new);
        if (orderAlreadyPaid(openOrder)) {
            throw new IllegalArgumentException(BEEN_CASHED_OUT);
        }
        openOrder.setStatus(PAID);
        return mapOrder(repo.save(openOrder));
    }

    private void throwWhenOrderDoesNotExist(Long orderId, String method) throws EntityNotFoundException {
        if (orderId == null || !repo.existsById(orderId)) {
            throw new EntityNotFoundException(String.format("You're trying to %s an order that doesn't exist", method));
        }
    }

    private boolean orderAlreadyPaid(OrderToCustomer order) {
        OrderToCustomer existingOrder = repo.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        return existingOrder.getStatus() == PAID;
    }

    private boolean orderAlreadyPaid(Long orderId) {
        OrderToCustomer existingOrder = repo.findById(orderId).orElseThrow(EntityNotFoundException::new);
        return existingOrder.getStatus() == PAID;
    }

    private void orderHasLessItemsThanTryingToReduce(OrderItemDTO orderItem, Long orderId) {
        getSpecificOrder(orderId).getOrderItems().forEach(itemOnOrder -> {
            if (itemOnOrder.equals(orderItem) && itemOnOrder.getQuantity() < orderItem.getQuantity()) {
                throw new IllegalArgumentException("It's not possible to remove more items than are on the order");
            }
        });
    }

    private boolean enoughItemsInStock(OrderItemDTO orderItem) {
        return orderItem.getQuantity() <= productService.getProductDetails(orderItem.getProduct().getId()).getAmountInStock();
    }

}



