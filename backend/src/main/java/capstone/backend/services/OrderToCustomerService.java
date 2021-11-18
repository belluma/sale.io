package capstone.backend.services;

import capstone.backend.repo.OrderToSupplierRepo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderToCustomerService {
    private final OrderToSupplierRepo repo;
    private final ProductService productService;
    private final OrderItemService orderItemService;
}
