package capstone.backend.model.dto.contact;

import capstone.backend.model.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeDTO extends ContactDTO{

    private String password;
    private List<UserRoles> roles;
}
