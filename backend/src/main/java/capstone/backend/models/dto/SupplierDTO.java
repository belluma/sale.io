package capstone.backend.models.dto;

import capstone.backend.models.Weekdays;
import capstone.backend.models.db.OrderToSupplier;
import capstone.backend.models.db.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.OneToMany;
import java.util.List;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {

    private List<Product> products;
    private List<OrderToSupplier> orders;
    private Weekdays oderDay;
}
