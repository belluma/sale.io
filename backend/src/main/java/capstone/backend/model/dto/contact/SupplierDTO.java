package capstone.backend.model.dto.contact;

import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.dto.ProductInfo;
import capstone.backend.model.dto.order.OrderToSupplierInfo;
import capstone.backend.model.enums.Weekday;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class SupplierDTO extends ContactDTO {

    private Long id;
    private List<OrderToSupplierInfo> orders;
    private Weekday orderDay;
    private List<ProductInfo> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SupplierDTO that = (SupplierDTO) o;
        return Objects.equals(id, that.id) && orderDay == that.orderDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, orderDay);
    }
}
