package capstone.backend.controller;

import capstone.backend.model.dto.ProductDTO;
import capstone.backend.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService productService) {
        this.service = productService;
    }

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
    @PutMapping("{id}")
    public ProductDTO editProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
        return service.editProduct(product);
    }

}
