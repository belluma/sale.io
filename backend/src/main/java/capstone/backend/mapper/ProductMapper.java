package capstone.backend.mapper;

import capstone.backend.model.db.Product;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.dto.contact.SupplierDTO;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ProductMapper {

    private ProductMapper() {

    }

    public static Product mapProduct(ProductDTO product) {
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
                .suppliers(mapProductList(product))
                .build();
    }

    public static ProductDTO mapProductWithDetails(Product product) {
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
                .suppliers(mapProductList(product))
                .build();
    }

    private static List<Supplier> mapProductList(ProductDTO product) {
        if (product.getSuppliers() == null) return List.of();
        return product
                .getSuppliers()
                .stream()
                .map(SupplierMapper::mapSupplier)
                .toList();
    }

    private static List<SupplierDTO> mapProductList(Product product) {
        if (product.getSuppliers() == null) return List.of();
        return product
                .getSuppliers()
                .stream()
                .map(SupplierMapper::mapSupplier)
                .toList();
    }
}
