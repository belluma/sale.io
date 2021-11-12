package capstone.backend.model.dto.order;


import capstone.backend.model.dto.contact.SupplierDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@With
public class OrderToSupplierDTO extends OrderDTO {


    private SupplierDTO supplier;

}
