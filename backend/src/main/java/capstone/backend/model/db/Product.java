package capstone.backend.model.db;

import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.db.order.OrderQuantity;
import capstone.backend.model.db.order.OrderToSupplier;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany
    @ToString.Exclude
    private List<Supplier> suppliers;
    private String stockCodeSupplier;
    @ManyToOne
    private Category category;
    private Float purchasePrice;
    private Float retailPrice;
    private int minAmount;
    private int maxAmount;
    private int unitSize;
    @ManyToMany
    @ToString.Exclude
    private Set<OrderQuantity> orderQuantity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
