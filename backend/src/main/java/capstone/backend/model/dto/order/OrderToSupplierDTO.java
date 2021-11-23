package capstone.backend.model.dto.order;


import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.model.enums.OrderToSupplierStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderToSupplierDTO extends OrderDTO {


    private SupplierDTO supplier;
    private OrderToSupplierStatus status;

    public  OrderToSupplierDTO(Long id, List<OrderItemDTO> orderItems, SupplierDTO supplier) {
        super(id, orderItems);
        this.supplier = supplier;
    }

}
