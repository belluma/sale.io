package capstone.backend.models.dto.contact;

import capstone.backend.crud.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends DataTransferObject {

    private String test;

//    private List<OrderFromCustomer> orders;
}
