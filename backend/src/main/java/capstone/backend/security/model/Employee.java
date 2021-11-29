package capstone.backend.security.model;

import capstone.backend.model.enums.UserRole;
import capstone.backend.model.db.contact.Contact;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Employee extends Contact {

    @Column(unique=true)
    private String username;
    private String password;
    @ElementCollection
    private List<UserRole> roles;

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(username, employee.username) && Objects.equals(password, employee.password) && Objects.equals(roles, employee.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, roles);
    }
}
