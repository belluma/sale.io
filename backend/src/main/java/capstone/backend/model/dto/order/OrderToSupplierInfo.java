package capstone.backend.model.dto.order;

import capstone.backend.model.db.order.OrderItemInfo;
import capstone.backend.model.enums.OrderToSupplierStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class OrderToSupplierInfo {

    private Long id;
    private List<OrderItemInfo> orderItems;
    private OrderToSupplierStatus status;

}
