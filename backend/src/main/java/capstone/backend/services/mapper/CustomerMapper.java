package capstone.backend.services.mapper;

import capstone.backend.crud.CrudMapper;
import capstone.backend.crud.DataTransferObject;
import capstone.backend.crud.DatabaseObject;
import capstone.backend.models.db.Customer;
import capstone.backend.models.dto.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements CrudMapper<CustomerDTO, Customer> {
    @Override
    public CustomerDTO mapToDto(DatabaseObject databaseObject) {
        return new CustomerDTO();
    }

    @Override
    public Customer mapToDbo(DataTransferObject databaseObject) {
        return new Customer();
    }
}
