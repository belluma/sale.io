package capstone.backend.model.db.order;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_orders")
public class OrderToCustomer extends Order {
}
