package capstone.backend.model.db.contact;

import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.enums.Weekday;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @OneToMany()
    @ToString.Exclude
    @JsonIgnore
    private Set<OrderToSupplier> orders;
    @ManyToMany(mappedBy = "suppliers")
    @JsonIgnore
    private Set<Product> products;


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

    public void updateProductList(Product product) {
        if(this.products == null){
            this.products = Set.of(product);
        }else {
            this.products.add(product);
        }
    }


}
