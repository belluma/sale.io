package capstone.backend.model.dto.order;


import capstone.backend.model.enums.OrderToCustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderToCustomerDTO extends OrderDTO {

    private OrderToCustomerStatus status;

    public OrderToCustomerDTO(Long id, List<OrderItemDTO> orderItems) {
        super(id, orderItems);
        this.status = OrderToCustomerStatus.OPEN;
    }

}
