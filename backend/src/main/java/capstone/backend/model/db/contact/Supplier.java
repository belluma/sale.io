package capstone.backend.model.db.contact;

import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.enums.Weekday;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "suppliers")
public class Supplier extends Contact {

    private Weekday orderDay;
    @OneToMany(fetch = FetchType.EAGER)
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
