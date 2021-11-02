package capstone.backend.security.model;

import capstone.backend.model.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {

    private String username;
    private String password;
    private List<UserRoles> roles;

    public EmployeeDTO(String username, String password){
        this.username = username;
        this.password = password;
    }

}
