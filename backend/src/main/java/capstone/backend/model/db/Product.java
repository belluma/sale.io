package capstone.backend.model.db;

import capstone.backend.model.db.contact.Supplier;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
