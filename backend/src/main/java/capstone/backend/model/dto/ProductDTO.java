package capstone.backend.model.dto;

import capstone.backend.model.db.Category;
import capstone.backend.model.dto.contact.SupplierDTO;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ProductDTO {

    private Long id;
    private String name;
    private Set<SupplierDTO> suppliers;
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
        ProductDTO that = (ProductDTO) o;
        return minAmount == that.minAmount && maxAmount == that.maxAmount && unitSize == that.unitSize && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(suppliers, that.suppliers) && Objects.equals(stockCodeSupplier, that.stockCodeSupplier) && Objects.equals(category, that.category) && Objects.equals(purchasePrice, that.purchasePrice) && Objects.equals(retailPrice, that.retailPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, suppliers, stockCodeSupplier, category, purchasePrice, retailPrice, minAmount, maxAmount, unitSize);
    }
}
