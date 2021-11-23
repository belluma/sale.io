package capstone.backend.model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderContainerDTO {
    private OrderToCustomerDTO order;
    private OrderItemDTO itemToAddOrRemove;

}
