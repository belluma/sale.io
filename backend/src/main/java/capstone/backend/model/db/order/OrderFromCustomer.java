package capstone.backend.model.db.order;



import capstone.backend.model.db.contact.Customer;

import javax.persistence.*;

@Entity
@Table(name = "cutomer_orders")
public class OrderFromCustomer extends Order{

    @ManyToOne
    private Customer customer;


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}