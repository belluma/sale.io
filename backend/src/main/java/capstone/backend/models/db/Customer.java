package capstone.backend.models.db;

import capstone.backend.crud.DatabaseObject;
import capstone.backend.models.dto.CustomerDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Customer extends DatabaseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String test;

//    @OneToMany
//    @ToString.Exclude
//    private List<OrderFromCustomer> orders;
public Customer(CustomerDTO customer, Long id){
    this.test = customer.getTest();
    this.id = id;
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
