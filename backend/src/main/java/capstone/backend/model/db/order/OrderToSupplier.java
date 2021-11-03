package capstone.backend.model.db.order;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name ="supplier_orders")
public class OrderToSupplier extends Order {

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
