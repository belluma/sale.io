package capstone.backend.model.dto.contact;

import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.enums.Weekdays;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

//@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class SupplierDTO extends ContactDTO{

    private Long id;
    private List<ProductDTO> products;
    private List<OrderToSupplier> orders;
    private Weekdays orderDay;


}
