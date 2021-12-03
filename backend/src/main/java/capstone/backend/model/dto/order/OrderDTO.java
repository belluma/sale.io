package capstone.backend.model.dto.order;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class OrderDTO {

    private Long id;
    private List<OrderItemDTO> orderItems;

    protected OrderDTO(Long id, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.orderItems = orderItems;
    }


}
