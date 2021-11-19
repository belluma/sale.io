package capstone.backend.services;


import capstone.backend.mapper.ProductMapper;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepo repo;

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

    public void receiveGoods(List<OrderItemDTO> receivedOrder)throws IllegalArgumentException {
        receivedOrder.forEach(item -> {
            if(item.getQuantity() < 0){
                throw new IllegalArgumentException("You can't receive orders with negative quantity count");
            }
        });
        receivedOrder
                .forEach(item -> {
                    Product productToReceive = repo.getById(item.getProduct().getId());
                    int newAmount = productToReceive.getAmountInStock() + item.getQuantity();
                    productToReceive.setAmountInStock(newAmount);
                    repo.save(productToReceive);
                });
    }

    public void substractStockWhenAddingItemToBill(OrderItem itemsToAddToBill) {
        Product productToReceive = repo.getById(itemsToAddToBill.getProduct().getId());
        int newAmount = productToReceive.getAmountInStock() - itemsToAddToBill.getQuantity()  ;
        productToReceive.setAmountInStock(newAmount);
        repo.save(productToReceive);
    }

    public boolean productExists(ProductDTO product) {
        return (product.getId() != null && repo.existsById(product.getId()));
    }
}
