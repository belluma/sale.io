package capstone.backend.models.db.order;


import capstone.backend.crud.DatabaseObject;
import capstone.backend.models.db.contact.Customer;

import javax.persistence.*;

@MappedSuperclass
public class Order extends DatabaseObject {



    //TODO find a good way to store products and quantity ordered
//    private HashMap<Product, Integer> productsOrdered;


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
