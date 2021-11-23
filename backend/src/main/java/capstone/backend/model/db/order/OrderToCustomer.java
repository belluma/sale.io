package capstone.backend.model.db.order;

import capstone.backend.model.enums.OrderStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@ToString
@Entity
@SuperBuilder
@With
@Data
@Table(name = "customer_orders")
public class OrderToCustomer extends Order {


    public OrderToCustomer(){}

    public OrderToCustomer(OrderStatus status){
        super(status);
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
