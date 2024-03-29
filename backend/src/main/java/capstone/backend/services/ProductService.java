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

import static capstone.backend.mapper.ProductMapper.mapProduct;
import static capstone.backend.mapper.ProductMapper.mapProductWithDetails;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepo repo;
    private final SupplierService supplierService;

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
        validateSuppliers(product);
        Product savedProduct = repo.save(mapProduct(product));
        supplierService.updateProductList(savedProduct);
        return mapProductWithDetails(savedProduct);
    }

    public ProductDTO editProduct(ProductDTO product) {
        if (!productExists(product)) {
            throw new EntityNotFoundException(String.format("Couldn't find a product with the id %d", product.getId()));
        }
        validateSuppliers(product);
        supplierService.updateProductList(mapProduct(product));
        return mapProductWithDetails(repo
                .save(mapProduct(product)));
    }

    private void validateSuppliers(ProductDTO product) {
        if (product.getSuppliers().isEmpty()){
            throw new IllegalArgumentException("You forgot to add a supplier to your product");
        }
        product.getSuppliers().forEach(supplier -> supplierService.getSupplierDetails(supplier.getId()));
    }

    public void receiveGoods(List<OrderItemDTO> receivedOrder) throws IllegalArgumentException {
        receivedOrder.forEach(item -> {
            if (item.getQuantity() < 0) {
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
        int newAmount = productToReceive.getAmountInStock() - itemsToAddToBill.getQuantity();
        productToReceive.setAmountInStock(newAmount);
        repo.save(productToReceive);
    }

    public boolean productExists(ProductDTO product) {
        return (product.getId() != null && repo.existsById(product.getId()));
    }

    public void resetAmountInStockWhenRemovingFromBill(OrderItem orderItem) {
        Product productToReceive = repo.getById(orderItem.getProduct().getId());
        int newAmount = productToReceive.getAmountInStock() + orderItem.getQuantity();
        productToReceive.setAmountInStock(newAmount);
        repo.save(productToReceive);
    }
}
