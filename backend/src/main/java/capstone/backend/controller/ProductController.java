package capstone.backend.controller;

import capstone.backend.model.dto.ProductDTO;
import capstone.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<ProductDTO> getAllProductsWithDetails() {
        return service.getAllProductsWithDetails();
    }

    @GetMapping("{id}")
    public ProductDTO getProductDetails(@PathVariable Long id) {
        return service.getProductDetails(id);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO product) {
        return service.createProduct(product);
    }

    @PutMapping("/{id}")
    public ProductDTO editProduct(@RequestBody ProductDTO product) {
        return service.editProduct(product);
    }

}
