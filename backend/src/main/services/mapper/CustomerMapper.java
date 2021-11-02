package capstone.backend.services.mapper;

import capstone.backend.crud.CrudMapper;
import capstone.backend.crud.DataTransferObject;
import capstone.backend.crud.DatabaseObject;
import capstone.backend.models.db.contact.Customer;
import capstone.backend.models.dto.contact.CustomerDTO;
import org.springframework.stereotype.Component;

public class CustomerMapper implements CrudMapper<CustomerDTO, Customer> {
    @Override
    public CustomerDTO mapToDto(DatabaseObject databaseObject) {
        return new CustomerDTO();
    }

    @Override
    public Customer mapToDbo(DataTransferObject databaseObject) {
        return new Customer();
    }
    public Customer mapToDbo(CustomerDTO customer) {
        return new Customer();
    }
}
