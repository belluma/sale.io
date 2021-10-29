package capstone.backend.models.dto;

import capstone.backend.models.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO extends ContactDTO{

    private String password;
    private List<UserRoles> roles;
}
