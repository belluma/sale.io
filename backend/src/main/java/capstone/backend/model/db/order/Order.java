package capstone.backend.model.db.order;



import capstone.backend.model.enums.OrderToSupplierStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany( cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderItem> orderItems;

    protected Order(List<OrderItem> orderItems){
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
