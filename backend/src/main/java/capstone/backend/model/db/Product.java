package capstone.backend.model.db;

import capstone.backend.model.db.contact.Supplier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@With
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany()
    @ToString.Exclude
    @JsonIgnore
    private Set<Supplier> suppliers;
    private String stockCodeSupplier;
    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Category category;
    private Float purchasePrice;
    private Float retailPrice;
    private int minAmount;
    private int maxAmount;
    private int unitSize;
    private int amountInStock;
    private String picture;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return minAmount == product.minAmount && maxAmount == product.maxAmount && unitSize == product.unitSize && amountInStock == product.amountInStock && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(suppliers, product.suppliers) && Objects.equals(stockCodeSupplier, product.stockCodeSupplier) && Objects.equals(category, product.category) && Objects.equals(purchasePrice, product.purchasePrice) && Objects.equals(retailPrice, product.retailPrice) && Objects.equals(picture, product.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, suppliers, stockCodeSupplier, category, purchasePrice, retailPrice, minAmount, maxAmount, unitSize, amountInStock, picture);
    }
}
