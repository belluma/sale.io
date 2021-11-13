package capstone.backend.services;

import capstone.backend.exception.model.EntityNotFoundException;
import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.mapper.OrderToSupplierMapper;
import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.repo.OrderToSupplierRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import static capstone.backend.mapper.OrderToSupplierMapper.mapOrder;

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
    public OrderToSupplierDTO createOrder(OrderToSupplierDTO order) throws EntityNotFoundException {
validateOrder(order);
        //        if (!checkIfProductsExistent(order)) {
//            throw new IllegalArgumentException("You tried to order a product that doesn't exist!");
//        }
//        if(repo.findById(order.getId()).isPresent()){
//            throw new EntityWithThisIdAlreadyExistException("An Order with this id already exists!");
//        }
//        if(!supplierService.checkIfSupplierExists(order.getSupplier().getId())){
//            throw new IllegalArgumentException("You tried to order from a supplier that doesn't exist!");
//        }
        order.setOrderItems(order
                        .getOrderItems()
                        .stream()
                        .map(orderItemService::addOrderItem)
                        .toList());
        return mapOrder(repo.save(mapOrder(order)));
    }

    private boolean checkIfProductsExistent(OrderToSupplierDTO order) {
        return !order
                .getOrderItems()
                .stream()
                .map(item -> productService.checkIfProductExists(item.getProduct().getId()))
                .toList()
                .contains(false);
    }
    private void validateOrder(OrderToSupplierDTO order) throws IllegalArgumentException, EntityWithThisIdAlreadyExistException{
        if (!checkIfProductsExistent(order)) {
            throw new IllegalArgumentException("You tried to order a product that doesn't exist!");
        }
        if(repo.findById(order.getId()).isPresent()){
            throw new EntityWithThisIdAlreadyExistException("An Order with this id already exists!");
        }
        if(!supplierService.checkIfSupplierExists(order.getSupplier().getId())){
            throw new IllegalArgumentException("You tried to order from a supplier that doesn't exist!");
        }
    }

}
