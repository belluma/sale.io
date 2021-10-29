package capstone.backend.models.db;

import capstone.backend.models.UserRoles;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Employee extends Contact {


    private String password;
    @ElementCollection
    private List<UserRoles> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Employee employee = (Employee) o;
        return getId() != null && Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
