package capstone.backend.model.db.contact;

import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.enums.Weekdays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

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
    @OneToMany
    @ToString.Exclude
    private List<OrderToSupplier> orders;
    private Weekdays orderDay;


    public Supplier() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(products, supplier.products) && Objects.equals(orders, supplier.orders) && orderDay == supplier.orderDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, orders, orderDay);
    }
}
