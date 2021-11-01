package capstone.backend.security.model;

import capstone.backend.models.db.contact.Employee;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AppUser {

    @Id
    private String username;
    private String password;
    @OneToOne
    private Employee employee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser appUser = (AppUser) o;
        return username != null && Objects.equals(username, appUser.username);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
