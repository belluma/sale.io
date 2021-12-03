package capstone.backend.model.dto.order;

import capstone.backend.model.enums.OrderToSupplierStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class OrderToSupplierInfo extends OrderDTO {

    private OrderToSupplierStatus status;


}
