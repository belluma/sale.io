package capstone.backend.model.dto;

import capstone.backend.crud.DataTransferObject;
import capstone.backend.model.db.Category;
import capstone.backend.model.db.contact.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends DataTransferObject {

    private Long id;

    private List<Supplier> suppliers;
    private String stockCodeSupplier;
    private Category category;
    private Float purchasePrice;
    private Float retailPrice;
    private int minAmount;
    private int maxAmount;
    private int unitSize;
}
