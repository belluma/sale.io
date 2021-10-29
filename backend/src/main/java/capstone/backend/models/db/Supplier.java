package capstone.backend.models.db;

import capstone.backend.models.Weekdays;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Supplier extends Contact {

    @ManyToMany
    @ToString.Exclude
    private List<Product> products;
    @OneToMany
    @ToString.Exclude
    private List<OrderToSupplier> orders;
    private Weekdays oderDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Supplier supplier = (Supplier) o;
        return getId() != null && Objects.equals(getId(), supplier.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
