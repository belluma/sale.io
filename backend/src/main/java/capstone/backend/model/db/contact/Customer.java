package capstone.backend.models.db.contact;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends Contact {

    private String test;

//    @OneToMany
//    @ToString.Exclude
//    private List<OrderFromCustomer> orders;



}
