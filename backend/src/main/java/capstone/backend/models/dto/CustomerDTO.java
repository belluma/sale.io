package capstone.backend.models.dto;

import capstone.backend.crud.DataTransferObject;
import capstone.backend.models.db.OrderFromCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends DataTransferObject {

    private String test;

//    private List<OrderFromCustomer> orders;
}
