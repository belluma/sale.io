package capstone.backend.mapper;

import capstone.backend.model.db.Product;
import capstone.backend.model.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private ProductMapper(){

    }

    public static Product mapProduct(ProductDTO product){
        return Product
                .builder()
                .id(product.getId())
                .name(product.getName())
                .stockCodeSupplier(product.getStockCodeSupplier())
                .category(product.getCategory())
                .purchasePrice(product.getPurchasePrice())
                .retailPrice(product.getRetailPrice())
                .minAmount(product.getMinAmount())
                .maxAmount(product.getMaxAmount())
                .unitSize(product.getUnitSize())
//                .suppliers(product.getSuppliers()
//                        .stream()
//                        .map(SupplierMapper::mapSupplier)
//                        .toList())
                .build();
    }
 public static ProductDTO mapProductWithDetails(Product product){
        return ProductDTO
                .builder()
                .id(product.getId())
                .name(product.getName())
                .stockCodeSupplier(product.getStockCodeSupplier())
                .category(product.getCategory())
                .purchasePrice(product.getPurchasePrice())
                .retailPrice(product.getRetailPrice())
                .minAmount(product.getMinAmount())
                .maxAmount(product.getMaxAmount())
                .unitSize(product.getUnitSize())
//                .suppliers(product.getSuppliers()
//                        .stream()
//                        .map(SupplierMapper::mapSupplier)
//                        .toList())
                .build();
    }
}
