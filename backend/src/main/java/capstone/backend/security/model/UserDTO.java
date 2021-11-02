package capstone.backend.security.model;

//import capstone.backend.models.dto.contact.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String username;
    private String password;
//    private EmployeeDTO employee;
}
