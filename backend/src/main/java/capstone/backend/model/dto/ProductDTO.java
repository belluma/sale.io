package capstone.backend.model.dto;

import capstone.backend.model.db.Category;
import capstone.backend.model.dto.contact.SupplierDTO;
import lombok.*;

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
}
