package capstone.backend.controller;

import capstone.backend.model.dto.order.OrderToCustomerDTO;
import capstone.backend.services.OrderToCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders_customers")
@RequiredArgsConstructor
public class OrderToCustomerController {

    private final OrderToCustomerService service;

    @GetMapping
    public List<OrderToCustomerDTO> getAllOrders() {
        return List.of();
    }
}
