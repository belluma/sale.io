package capstone.backend.model.dto.contact;

import capstone.backend.model.dto.ProductInfo;
import capstone.backend.model.dto.order.OrderToSupplierInfo;
import capstone.backend.model.enums.Weekday;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class SupplierInfo  extends ContactDTO{

    private List<OrderToSupplierInfo> orders;
    private Weekday orderDay;
    private List<ProductInfo> products;
}
