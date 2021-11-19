package capstone.backend.model.dto.order;


import capstone.backend.model.enums.OrderToCustomerStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class OrderToCustomerDTO extends OrderDTO {

    private OrderToCustomerStatus status;
}
