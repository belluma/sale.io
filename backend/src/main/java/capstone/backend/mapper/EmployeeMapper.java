package capstone.backend.mapper;

import capstone.backend.crud.CrudMapper;
import capstone.backend.crud.DataTransferObject;
import capstone.backend.crud.DatabaseObject;
import capstone.backend.model.db.contact.Employee;
import capstone.backend.model.dto.contact.EmployeeDTO;

public class EmployeeMapper implements CrudMapper<EmployeeDTO, Employee> {
    @Override
    public EmployeeDTO mapToDto(DatabaseObject employee) {
        return new EmployeeDTO();//.builder().build();
    }

    @Override
    public Employee mapToDbo(DataTransferObject databaseObject) {
        return new Employee();//.builder().build();
    }
}
