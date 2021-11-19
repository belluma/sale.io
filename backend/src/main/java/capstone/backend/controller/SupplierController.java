package capstone.backend.controller;

import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService service;

    @GetMapping
    public List<SupplierDTO> getAllSuppliersWithDetails() {
        return service.getAllSuppliersWithDetails();
    }

    @GetMapping("{id}")
    public SupplierDTO getSupplierDetails(@PathVariable Long id) {
        return service.getSupplierDetails(id);
    }

    @PostMapping
    public SupplierDTO createSupplier(@RequestBody SupplierDTO supplier) {
        return service.createSupplier(supplier);
    }
    @PutMapping("{id}")
    public SupplierDTO editSupplier( @RequestBody SupplierDTO supplier, @PathVariable Long id) {
        return service.editSupplier(supplier);
    }

}
