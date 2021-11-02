package capstone.backend.security.service;

import capstone.backend.security.model.Employee;
import capstone.backend.security.model.EmployeeDTO;

public class UserMapper {
    public static Employee mapUser(EmployeeDTO user) {
        return Employee
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
public static EmployeeDTO mapUser(Employee user) {
        return EmployeeDTO
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
