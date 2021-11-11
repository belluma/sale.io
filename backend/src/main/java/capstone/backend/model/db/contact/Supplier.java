package capstone.backend.model.db.contact;

import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.Order;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.enums.Weekdays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "suppliers")
public class Supplier extends Contact {

    @ManyToMany
    @ToString.Exclude
    private List<Product> products;
    private Weekdays orderDay;
    @OneToMany
    @ToString.Exclude
    private Set<OrderToSupplier> orders;

    public Supplier() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
