package capstone.backend.model.dto.order;

import capstone.backend.model.db.Product;
import capstone.backend.model.db.contact.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFromCustomerDTO {

    private Long id;
    private HashMap<Product, Integer> productsOrdered;
    private Customer customer;
}
