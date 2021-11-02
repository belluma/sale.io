package capstone.backend.model.dto.contact;

import capstone.backend.model.UserRoles;
import capstone.backend.security.model.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeDTO extends ContactDTO{

    private String password;
    private List<UserRoles> roles;
    private UserDTO user;
}
