package capstone.backend.models.db;


import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class OrderFromCustomer {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    //TODO find a good way to store products and quantity ordered
//    private HashMap<Product, Integer> productsOrdered;
    @ManyToOne
    private Customer customer;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
