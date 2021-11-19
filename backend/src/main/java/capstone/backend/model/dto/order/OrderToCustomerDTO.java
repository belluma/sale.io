package capstone.backend.model.dto.order;


import capstone.backend.model.enums.OrderToCustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderToCustomerDTO extends OrderDTO {

    private OrderToCustomerStatus status;
}
