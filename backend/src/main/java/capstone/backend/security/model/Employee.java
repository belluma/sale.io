package capstone.backend.security.model;

import capstone.backend.model.enums.UserRole;
import capstone.backend.model.db.contact.Contact;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Employee appUser = (Employee) o;
        return username != null && Objects.equals(username, appUser.username);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
