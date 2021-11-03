package capstone.backend.security.model;

import capstone.backend.model.UserRoles;
import capstone.backend.model.dto.contact.ContactDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class EmployeeDTO extends ContactDTO {

    private String username;
    private String password;
    private List<UserRoles> roles;

    public EmployeeDTO(String username, String password){
        super();
        this.username = username;
        this.password = password;
    }

}
