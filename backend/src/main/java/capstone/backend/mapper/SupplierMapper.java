package capstone.backend.mapper;

import capstone.backend.model.db.Product;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.dto.contact.SupplierDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SupplierMapper {

    private SupplierMapper() {

    }

    public static Supplier mapSupplier(SupplierDTO supplier) {
        return Supplier
                .builder()
                .id(supplier.getId())
                .firstName(supplier.getFirstName())
                .lastName(supplier.getLastName())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .products(mapProductList(supplier))
                .orders(List.of())
                .orderDay(supplier.getOrderDay())
                .build();
    }

    public static SupplierDTO mapSupplier(Supplier supplier) {
        return SupplierDTO
                .builder()
                .id(supplier.getId())
                .firstName(supplier.getFirstName())
                .lastName(supplier.getLastName())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .products(mapProductList(supplier))
                .orders(List.of())
                .orderDay(supplier.getOrderDay())
                .build();
    }

    private static List<Product> mapProductList(SupplierDTO supplier) {
        if (supplier.getProducts() == null) return List.of();
        return supplier
                .getProducts()
                .stream()
                .map(ProductMapper::mapProduct)
                .toList();
    }

    private static List<ProductDTO> mapProductList(Supplier supplier) {
        if (supplier.getProducts() == null) return List.of();
        return supplier
                .getProducts()
                .stream()
                .map(ProductMapper::mapProductWithDetails)
                .toList();
    }
}
