package capstone.backend.model.dto;

import capstone.backend.model.db.Category;
import lombok.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ProductInfo {

    private Long id;
    private String name;
    private String stockCodeSupplier;
    private Category category;
    private Float purchasePrice;
    private Float retailPrice;
    private int minAmount;
    private int maxAmount;
    private int unitSize;
    private int amountInStock;
    private String picture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfo that = (ProductInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
