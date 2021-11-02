package capstone.backend.models.db.order;


import capstone.backend.models.db.contact.Customer;

import javax.persistence.*;

@Entity
@Table(name = "cutomer_orders")
public class OrderFromCustomer extends Order{

    //TODO find a good way to store products and quantity ordered
//    private HashMap<Product, Integer> productsOrdered;
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
