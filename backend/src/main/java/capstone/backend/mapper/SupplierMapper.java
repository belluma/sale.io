package capstone.backend.mapper;

import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.contact.SupplierDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                .orders(Set.of())
                .orderDay(supplier.getOrderDay())
                .picture(supplier.getPicture())
//                .products(supplier.getProducts().stream().map(ProductMapper::mapProduct).collect(Collectors.toSet()))
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
                .orders(List.of())
                .orderDay(supplier.getOrderDay())
                .picture(supplier.getPicture())
                .products(supplier.getProducts() == null ? List.of() : supplier.getProducts().stream().map(ProductMapper::mapToProductinfo).toList())
                .build();
    }

}
