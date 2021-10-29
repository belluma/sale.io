package capstone.backend.models.db;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Customer extends Contact{


    @OneToMany
    @ToString.Exclude
    private List<OrderFromCustomer> orders;



}
