package capstone.backend.model.db.contact;

import capstone.backend.crud.DatabaseObject;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class Contact extends DatabaseObject {

    private String name;
    private String email;
    private String phone;
    private String picture;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
