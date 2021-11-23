package capstone.backend.controller;


import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.model.enums.OrderToSupplierStatus;
import capstone.backend.services.OrderToSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders_suppliers")
@RequiredArgsConstructor
public class OrderToSupplierController {

    private final OrderToSupplierService service;

    @GetMapping
    public List<OrderToSupplierDTO> getAllOrders() {
        return service.getAllOrders();
    }

    @PostMapping
    public OrderToSupplierDTO createOrder(@RequestBody OrderToSupplierDTO order) {
        return service.createOrder(order);
    }

    @PutMapping
    public OrderToSupplierDTO receiveOrder(@RequestBody OrderToSupplierDTO order, @RequestParam Long id, @RequestParam OrderToSupplierStatus status) {
        return service.receiveOrder(order, status);
    }
}
