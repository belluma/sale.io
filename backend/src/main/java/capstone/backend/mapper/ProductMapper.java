package capstone.backend.mapper;

import capstone.backend.model.db.Product;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.dto.ProductInfo;
import capstone.backend.model.dto.contact.SupplierDTO;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class ProductMapper {

    private ProductMapper() {

    }

    public static Product mapProduct(ProductDTO product) {
        return Product
                .builder()
                .id(product.getId())
                .name(product.getName())
                .suppliers(mapSupplierList(product))
                .stockCodeSupplier(product.getStockCodeSupplier())
                .category(product.getCategory())
                .purchasePrice(product.getPurchasePrice())
                .retailPrice(product.getRetailPrice())
                .minAmount(product.getMinAmount())
                .maxAmount(product.getMaxAmount())
                .unitSize(product.getUnitSize())
                .amountInStock(product.getAmountInStock())
                .picture(product.getPicture())
                .build();
    }

    public static ProductDTO mapProductWithDetails(Product product) {
        return ProductDTO
                .builder()
                .id(product.getId())
                .name(product.getName())
                .suppliers(mapSupplierList(product))
                .stockCodeSupplier(product.getStockCodeSupplier())
                .category(product.getCategory())
                .purchasePrice(product.getPurchasePrice())
                .retailPrice(product.getRetailPrice())
                .minAmount(product.getMinAmount())
                .maxAmount(product.getMaxAmount())
                .unitSize(product.getUnitSize())
                .amountInStock(product.getAmountInStock())
                .picture(product.getPicture())
                .build();
    }

    public static ProductInfo mapToProductinfo(Product product){
        return ProductInfo
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
                .amountInStock(product.getAmountInStock())
                .picture(product.getPicture())
                .build();
    }

    private static Set<Supplier> mapSupplierList(ProductDTO product) {
        if (product.getSuppliers() == null) return Set.of();
        return product
                .getSuppliers()
                .stream()
                .map(SupplierMapper::mapSupplier)
                .collect(Collectors.toSet());
    }

    private static Set<SupplierDTO> mapSupplierList(Product product) {
        if (product.getSuppliers() == null) return Set.of();
        return product
                .getSuppliers()
                .stream()
                .map(SupplierMapper::mapSupplier)
                .collect(Collectors.toSet());
    }
}
