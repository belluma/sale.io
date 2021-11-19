package capstone.backend.model.dto.order;

import capstone.backend.model.dto.ProductDTO;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class OrderItemDTO {

    private Long id;
    private ProductDTO product;
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDTO that = (OrderItemDTO) o;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
