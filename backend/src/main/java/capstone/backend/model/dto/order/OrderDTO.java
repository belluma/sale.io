package capstone.backend.model.dto.order;


import capstone.backend.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class OrderDTO {

    protected OrderDTO (Long id, List<OrderItemDTO> orderItems){
        this.id = id;
        this.orderItems = orderItems;
    }

    private Long id;
    private List<OrderItemDTO> orderItems;
    private OrderStatus status;


}
