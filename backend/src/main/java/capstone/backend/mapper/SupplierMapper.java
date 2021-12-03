package capstone.backend.mapper;

import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.contact.SupplierDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

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
                .orders(supplier.getOrders() == null ? List.of(): supplier.getOrders().stream().map(OrderToSupplierMapper::mapToOrderInfo).toList())
                .orderDay(supplier.getOrderDay())
                .picture(supplier.getPicture())
                .products(supplier.getProducts() == null ? List.of() : supplier.getProducts().stream().map(ProductMapper::mapToProductInfo).toList())
                .build();
    }

}
