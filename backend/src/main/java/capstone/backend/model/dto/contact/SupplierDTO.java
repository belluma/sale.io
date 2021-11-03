package capstone.backend.model.dto.contact;

import capstone.backend.model.Weekdays;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.OrderToSupplier;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
//@AllArgsConstructor
public class SupplierDTO extends ContactDTO{

    private List<Product> products;
    private List<OrderToSupplier> orders;
    private Weekdays oderDay;

}
