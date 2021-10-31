package capstone.backend.models.db.contact;

import capstone.backend.models.Weekdays;
import capstone.backend.models.db.order.OrderToSupplier;
import capstone.backend.models.db.Product;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
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
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
