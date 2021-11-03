package capstone.backend.model.db.contact;

import capstone.backend.model.Weekdays;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.OrderToSupplier;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@ToString
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
