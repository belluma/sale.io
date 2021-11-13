package capstone.backend.controller;


import capstone.backend.model.dto.order.OrderToSupplierDTO;
import capstone.backend.services.OrderToSupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders_suppliers")
public class OrderToSupplierController {

    private final OrderToSupplierService service;

    public OrderToSupplierController(OrderToSupplierService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderToSupplierDTO> getAllOrders(){
        return service.getAllOrders();
    }

    @PostMapping()
    public OrderToSupplierDTO createOrder(@RequestBody OrderToSupplierDTO order){
        return service.createOrder(order);
    }
}