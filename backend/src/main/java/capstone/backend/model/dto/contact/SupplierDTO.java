package capstone.backend.model.dto.contact;

import capstone.backend.model.enums.Weekdays;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.OrderToSupplier;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class SupplierDTO extends ContactDTO{

    private List<Product> products;
    private List<OrderToSupplier> orders;
    private Weekdays oderDay;

}
