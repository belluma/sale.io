package capstone.backend.model.db.order;

import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.enums.OrderToSupplierStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@ToString
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@With
@Data
@Table(name = "supplier_orders")
public class OrderToSupplier extends Order {

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Supplier supplier;
    private OrderToSupplierStatus status;

    public OrderToSupplier(Long id, List<OrderItem> orderItems, OrderToSupplierStatus status, Supplier supplier) {
        super(id, orderItems);
        this.status = status;
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
