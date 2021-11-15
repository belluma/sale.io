package capstone.backend.model.dto.order;


import capstone.backend.model.dto.contact.SupplierDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@With
public class OrderToSupplierDTO extends OrderDTO {


    private SupplierDTO supplier;

    public  OrderToSupplierDTO(Long id, List<OrderItemDTO> orderItems, SupplierDTO supplier) {
        super(id, orderItems);
        this.supplier = supplier;
    }
}
