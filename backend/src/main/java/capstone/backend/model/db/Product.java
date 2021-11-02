package capstone.backend.models.db;

import capstone.backend.crud.DatabaseObject;
import capstone.backend.models.db.contact.Supplier;
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
public class Product extends DatabaseObject {

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
