package capstone.backend.model.db.order;

import capstone.backend.model.enums.OrderToCustomerStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@ToString
@Entity
@SuperBuilder
@With
@Data
@Table(name = "customer_orders")
public class OrderToCustomer extends Order {

    private OrderToCustomerStatus status;

    public OrderToCustomer() {
    }

    public OrderToCustomer(OrderToCustomerStatus status) {
        super(List.of());
        this.status = status;
    }

    public OrderToCustomer(List<OrderItem> orderItems, OrderToCustomerStatus status) {
        super(orderItems);
        this.status = status;
    }

    public OrderToCustomer(Long id, List<OrderItem> orderItems, OrderToCustomerStatus status) {
        super(id, orderItems);
        this.status = status;
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
