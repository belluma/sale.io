package capstone.backend.controller;

import capstone.backend.crud.CrudController;
import capstone.backend.crud.CrudMapper;
import capstone.backend.crud.CrudService;
import capstone.backend.mapper.EmployeeMapper;
import capstone.backend.model.db.contact.Employee;
import capstone.backend.model.dto.contact.EmployeeDTO;

public class EmployeeController extends CrudController<EmployeeDTO, Employee> {


    public EmployeeController(CrudService<EmployeeDTO, Employee> service) {
        super(service, new EmployeeMapper());
    }
}