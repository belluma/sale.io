package capstone.backend.services;


import capstone.backend.mapper.ProductMapper;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.repo.ProductRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
                .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find a product with the id %d", id)));
    }

    public ProductDTO createProduct(ProductDTO product) throws EntityWithThisIdAlreadyExistException {
        if (productExists(product)) {
            throw new EntityWithThisIdAlreadyExistException(String.format("Product %s already has the id %d", product.getName(), product.getId()));
        }
        return ProductMapper.mapProductWithDetails(repo.
                save(ProductMapper.mapProduct(product)));
    }

    public ProductDTO editProduct(ProductDTO product) {
        if (!productExists(product)) {
            throw new EntityNotFoundException(String.format("Couldn't find a product with the id %d", product.getId()));
        }
        return ProductMapper.mapProductWithDetails(repo
                .save(ProductMapper.mapProduct(product)));
    }

    public void adjustAmountInStock(List<OrderItemDTO> receivedOrder) {
        receivedOrder
                .forEach(item -> {
                    Optional<Product> productToReceive = repo.findById(item.getProduct().getId());
                    productToReceive.ifPresent(product -> product.setAmountInStock(product.getAmountInStock() + item.getProduct().getAmountInStock()));
                });
    }

    public boolean productExists(ProductDTO product) {
        return (product.getId() != null && repo.existsById(product.getId()));
    }
}
