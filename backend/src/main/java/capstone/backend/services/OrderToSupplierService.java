package capstone.backend.services;

import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.mapper.OrderToSupplierMapper;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.model.enums.OrderToSupplierStatus;
import capstone.backend.repo.OrderToSupplierRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static capstone.backend.mapper.OrderToSupplierMapper.mapOrder;
import static capstone.backend.model.enums.OrderToSupplierStatus.RECEIVED;

@Service
@RequiredArgsConstructor
public class OrderToSupplierService {

    private final OrderToSupplierRepo repo;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final SupplierService supplierService;

    public List<OrderToSupplierDTO> getAllOrders() {
        return repo.findAll()
                .stream()
                .map(OrderToSupplierMapper::mapOrder)
                .toList();
    }

    public OrderToSupplierDTO createOrder(OrderToSupplierDTO order) throws  EntityWithThisIdAlreadyExistException {
        if (orderExists(order)) {
            throw new EntityWithThisIdAlreadyExistException(String.format("An order with id %d already exists!", order.getId()));
        }
        validateNewOrder(order);
        order.setOrderItems(order
                .getOrderItems()
                .stream()
                .map(orderItemService::addOrderItem)
                .toList());
        OrderToSupplier savedOrder = repo.save(mapOrder(order));
//        supplierService.updateOrderList(savedOrder);
        return mapOrder(savedOrder);
    }

    private void validateNewOrder(OrderToSupplierDTO order) throws IllegalArgumentException {
        productsExist(order, "You tried to order a product that doesn't exist!");
        if (!supplierService.supplierExists(order.getSupplier().getId())) {
            throw new IllegalArgumentException("You tried to order from a supplier that doesn't exist!");
        }
        if (supplierDoesNotCarryProduct(order)) {
            throw new IllegalArgumentException("The supplier doesn't carry one or several of the items you tried to order!");
        }
    }

    public OrderToSupplierDTO receiveOrder(OrderToSupplierDTO order, OrderToSupplierStatus status) throws EntityNotFoundException, IllegalArgumentException {
        validateOrderToReceive(order, status);
        productService.receiveGoods(order.getOrderItems());
        OrderToSupplier orderToReceive = repo.getById(order.getId());
        orderToReceive.setStatus(RECEIVED);
        return mapOrder(repo.save(orderToReceive));
    }

    private void validateOrderToReceive(OrderToSupplierDTO order, OrderToSupplierStatus status){
        if (status != RECEIVED) {
            throw new IllegalArgumentException("Your request couldn't be processed!");
        }
        if (!orderExists(order)) {
            throw new EntityNotFoundException("The order you're trying to receive doesn't exist");
        }
        if(order.getStatus() == RECEIVED){
            throw new IllegalArgumentException("The order you're trying to receive has already been received");
        }
        productsExist(order, "Your order contains products that don't exist in your database");
    }

    private boolean orderExists(OrderToSupplierDTO order) {
        return (order.getId() != null && repo.existsById(order.getId()));
    }
    private void productsExist(OrderToSupplierDTO order, String message) {
        if (!productsExist(order)) {
            throw new IllegalArgumentException(message);
        }
    }

    private boolean productsExist(OrderToSupplierDTO order) {
        return !order
                .getOrderItems()
                .stream()
                .map(item -> productService.productExists(item.getProduct()))
                .toList()
                .contains(false);
    }

    private boolean supplierDoesNotCarryProduct(OrderToSupplierDTO order) {
        return order
                .getOrderItems()
                .stream()
                .anyMatch(item -> !extractSupplierIds(item.getProduct()).contains(order.getSupplier().getId()));
    }

    private List<Long> extractSupplierIds(ProductDTO product) {
        return product
                .getSuppliers()
                .stream()
                .map(SupplierDTO::getId)
                .toList();
    }

}
