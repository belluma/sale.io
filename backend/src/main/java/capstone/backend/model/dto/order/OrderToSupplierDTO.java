package capstone.backend.model.dto.order;


import capstone.backend.model.dto.contact.SupplierDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@With
public class OrderToSupplierDTO extends OrderDTO {


    private SupplierDTO supplier;

}
