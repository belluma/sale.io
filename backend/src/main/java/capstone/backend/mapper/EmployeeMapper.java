package capstone.backend.mapper;

import capstone.backend.security.model.Employee;
import capstone.backend.security.model.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

        public static Employee mapEmployee(EmployeeDTO employee) {
            return Employee
                    .builder()
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .email(employee.getEmail())
                    .phone(employee.getPhone())
                    .picture(employee.getPicture())
                    .username(employee.getUsername())
                    .password(employee.getPassword())
                    .picture(employee.getPicture())
                    .build();
        }
        public static EmployeeDTO mapEmployee(Employee employee) {
            return EmployeeDTO
                    .builder()
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .email(employee.getEmail())
                    .phone(employee.getPhone())
                    .picture(employee.getPicture())
                    .username(employee.getUsername())
                    .password(employee.getPassword())
                    .picture(employee.getPicture())
                    .build();
        }
        public EmployeeDTO mapEmployeeAndConcealData(Employee employee){
            return EmployeeDTO
                    .builder()
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .picture(employee.getPicture())
                    .username(employee.getUsername())
                    .build();
        }
}
