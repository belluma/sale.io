package capstone.backend.models.dto.contact;

import capstone.backend.models.Weekdays;
import capstone.backend.models.db.order.OrderToSupplier;
import capstone.backend.models.db.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO extends ContactDTO{

    private List<Product> products;
    private List<OrderToSupplier> orders;
    private Weekdays oderDay;
}
