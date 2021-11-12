package capstone.backend.services;

import capstone.backend.exception.model.EntityNotFoundException;
import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.mapper.OrderToSupplierMapper;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.repo.OrderToSupplierRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static capstone.backend.mapper.OrderToSupplierMapper.mapOrder;
import static capstone.backend.mapper.SupplierMapper.mapSupplier;

@Service
public class OrderToSupplierService {

    private final OrderToSupplierRepo repo;
    private final ProductService productService;
    private final OrderItemService qtyService;

    public OrderToSupplierService(OrderToSupplierRepo repo, ProductService productService, OrderItemService qtyService) {
        this.repo = repo;
        this.productService = productService;
        this.qtyService = qtyService;
    }

    public List<OrderToSupplierDTO> getAllOrders() {
        return repo.findAll()
                .stream()
                .map(OrderToSupplierMapper::mapOrder)
                .toList();
    }
    public OrderToSupplierDTO createOrder(OrderToSupplierDTO order) throws EntityNotFoundException {
        if (!checkIfProductsExistant(order)) {
            throw new EntityNotFoundException("You tried to order a product that doesn't exist!");
        }
        if(repo.findById(order.getId()).isPresent()){
            throw new EntityWithThisIdAlreadyExistException("An Order with this id already exists!");
        }
        OrderToSupplier persisted = repo.save(mapOrder(order));
        persisted.setSupplier(mapSupplier(order.getSupplier()));
        List<OrderItem> qties = new ArrayList<>();
        order.getOrderQuantity().forEach(qty ->
                qties.add(qty));//qtyService.addOrderQuantity(qty)));//.withOrderId(persisted.getId()))));
        persisted.setOrderQuantity(qties);
        return mapOrder(persisted);
//        return mapOrder(repo.save(persisted));
    }

    private boolean checkIfProductsExistant(OrderToSupplierDTO order) {
        return !order
                .getOrderQuantity()
                .stream()
//                .map(qty -> productService.checkIfProductExists(qty.getProductId()))
                .toList()
                .contains(false);
    }

}
