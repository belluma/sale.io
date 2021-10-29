package capstone.backend.models.db;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public abstract class Contact {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    //TODO what's the best way to store and access files in Db?
    private String picture;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Contact contact = (Contact) o;
        return id != null && Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
