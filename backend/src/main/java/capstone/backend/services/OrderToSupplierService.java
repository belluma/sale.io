package capstone.backend.services;

import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.mapper.OrderToSupplierMapper;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.model.enums.OrderStatus;
import capstone.backend.repo.OrderToSupplierRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static capstone.backend.mapper.OrderToSupplierMapper.mapOrder;
import static capstone.backend.model.enums.OrderStatus.RECEIVED;

@Service
public class OrderToSupplierService {

    private final OrderToSupplierRepo repo;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final SupplierService supplierService;

    public OrderToSupplierService(OrderToSupplierRepo repo, ProductService productService, OrderItemService orderItemService, SupplierService supplierService) {
        this.repo = repo;
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.supplierService = supplierService;
    }

    public List<OrderToSupplierDTO> getAllOrders() {
        return repo.findAll()
                .stream()
                .map(OrderToSupplierMapper::mapOrder)
                .toList();
    }

    public OrderToSupplierDTO createOrder(OrderToSupplierDTO order) throws EntityNotFoundException, EntityWithThisIdAlreadyExistException {
        if (orderExists(order)) {
            throw new EntityWithThisIdAlreadyExistException("An Order with this id already exists!");
        }
        validateNewOrder(order);
        order.setOrderItems(order
                .getOrderItems()
                .stream()
                .map(orderItemService::addOrderItem)
                .toList());
        return mapOrder(repo.save(mapOrder(order)));
    }

    public OrderToSupplierDTO receiveOrder(OrderToSupplierDTO order, OrderStatus status) throws EntityNotFoundException, IllegalArgumentException {
        if (status != RECEIVED) {
            throw new IllegalArgumentException("We couldn't process your request!");
        }
        if (!orderExists(order)) {
            throw new EntityNotFoundException("The order you're trying to receive doesn't exist");
        }
        if(order.getStatus() == RECEIVED){
            throw new IllegalArgumentException("The order you're trying to receive has already been received");
        }
        productService.adjustAmountInStock(order.getOrderItems());
        repo.findById(order.getId()).ifPresent(receivedOrder -> receivedOrder.setStatus(RECEIVED));
        return mapOrder(repo.findById(order.getId()).orElseThrow());
    }

    private boolean orderExists(OrderToSupplierDTO order) {
        return (order.getId() != null && repo.existsById(order.getId()));
    }

    private void productsExist(OrderToSupplierDTO order, String message) {
        if (!productsExist(order)) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateNewOrder(OrderToSupplierDTO order) throws IllegalArgumentException, EntityWithThisIdAlreadyExistException {
        productsExist(order, "You tried to order a product that doesn't exist!");
        if (!supplierService.supplierExists(order.getSupplier().getId())) {
            throw new IllegalArgumentException("You tried to order from a supplier that doesn't exist!");
        }
        if (supplierDoesNotCarryProduct(order)) {
            throw new IllegalArgumentException("The supplier doesn't carry one or several of the items you tried to order!");
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
