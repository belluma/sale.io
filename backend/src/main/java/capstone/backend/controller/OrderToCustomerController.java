package capstone.backend.controller;

import capstone.backend.model.dto.order.OrderContainerDTO;
import capstone.backend.model.dto.order.OrderToCustomerDTO;
import capstone.backend.services.OrderToCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders_customers")
@RequiredArgsConstructor
public class OrderToCustomerController {

    private final OrderToCustomerService service;

    @GetMapping("all")
    public List<OrderToCustomerDTO> getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping()
    public List<OrderToCustomerDTO> getAllOpenOrders() {
        return service.getAllOpenOrders();
    }

    @PostMapping
    public OrderToCustomerDTO createOrder(){
        return service.createEmptyOrder();
    }

    @PutMapping("add")
    public OrderToCustomerDTO addItemsToOrder(@RequestParam Long id, @RequestBody OrderContainerDTO order){
        return service.addItemsToOrder(id, order.getItemToAddOrRemove(), order.getOrder());
    }
    @PutMapping("remove")
    public OrderToCustomerDTO removeItemsFromOrder(@RequestParam Long id, @RequestBody OrderContainerDTO order){
        return service.removeItemsFromOrder(id, order.getItemToAddOrRemove(), order.getOrder());
    }

}
