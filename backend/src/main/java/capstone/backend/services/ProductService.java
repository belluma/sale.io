package capstone.backend.services;


import capstone.backend.mapper.ProductMapper;
import capstone.backend.model.db.Product;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.exception.ProductIdAlreadyTakenException;
import capstone.backend.model.exception.ProductNotFoundException;
import capstone.backend.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepo repo;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }


    public List<ProductDTO> getAllProductsWithDetails() {
        return repo
                .findAll()
                .stream()
                .map(ProductMapper::mapProductWithDetails)
                .toList();
    }

    public ProductDTO getProductDetails(Long id) {
        return repo
                .findById(id)
                .map(ProductMapper::mapProductWithDetails)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Couldn't find a product with the id %d", id)));
    }

    public ProductDTO createProduct(ProductDTO product) throws ProductIdAlreadyTakenException {
        if (repo
                .findById(product.getId())
                .isPresent()) {
            throw new ProductIdAlreadyTakenException(String.format("Product %s already has the id %d", product.getName(), product.getId()));
        }


        return ProductMapper.mapProductWithDetails(repo.
                save(ProductMapper.mapProduct(product)));
    }

    public ProductDTO editProduct(ProductDTO product) {
        if (repo
                .findById(product.getId())
                .isEmpty()) {
            throw new ProductNotFoundException(String.format("Couldn't find a product with the id %d", product.getId()));
        }
        return ProductMapper.mapProductWithDetails(repo
                .save(ProductMapper.mapProduct(product)));
    }
}
