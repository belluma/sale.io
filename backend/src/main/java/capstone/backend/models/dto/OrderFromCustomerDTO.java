package capstone.backend.models.dto;

import capstone.backend.models.db.Customer;
import capstone.backend.models.db.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class OrderFromCustomerDTO {

    private Long id;
    private HashMap<Product, Integer> productsOrdered;
    private Customer customer;
}
