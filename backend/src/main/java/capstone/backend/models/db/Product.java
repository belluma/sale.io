package capstone.backend.models.db;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Product {
    @Id
    @Column(name = "id", nullable = false)
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

}
