package capstone.backend.services;


import capstone.backend.mapper.ProductMapper;
import capstone.backend.model.db.Product;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo repo;
    private final ProductMapper mapper;

    public ProductService(ProductRepo repo, ProductMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }


    public List<ProductDTO> getAllProductsWithDetails() {
        return repo
                .findAll()
                .stream()
                .map(mapper::mapWithDetails)
                .toList();
    }

    public ProductDTO getProductDetails(Long id) {
        return repo
                .findById(id)
                .map(mapper::mapWithDetails)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Couldn't find a product with the id %d", id)));
    }

    public ProductDTO createProduct(ProductDTO product) throws ProductAlreadyExistsException {
        if (repo
                .findById(product.getId())
                .isPresent()) {
            throw new ProductAlreadyExistsException(String.format("Product %s already has the id %d", product.getName(), product.getId()));
        }
        return mapper.mapWithDetails(repo.
                save(mapper.mapProduct(product))
                .map(mapper::mapWithDetails));
    }

    public ProductDTO editProduct(ProductDTO product) {
        if (repo
                .findById(product.getId())
                .isEmpty()) {
            throw new ProductNotFoundException(String.format("Couldn't find a product with the id %d", id));
        }
        return mapper.mapWithDetails(repo
                .save(mapper.mapProduct(product)));
    }
}
