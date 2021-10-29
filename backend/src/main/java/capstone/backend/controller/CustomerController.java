package capstone.backend.controller;

import capstone.backend.crud.CrudController;
import capstone.backend.crud.CrudMapper;
import capstone.backend.crud.CrudService;
import capstone.backend.models.db.Customer;
import capstone.backend.models.dto.CustomerDTO;
import capstone.backend.services.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController extends CrudController<CustomerDTO, Customer> {

    private final CrudMapper<CustomerDTO, Customer> mapper = new CustomerMapper();

    @Autowired
    public CustomerController(CrudService<CustomerDTO, Customer> service, CrudMapper<CustomerDTO,Customer> mapper) {
        super(service, mapper);
    }
}
