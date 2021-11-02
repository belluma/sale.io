package capstone.backend.model.db.order;


import capstone.backend.crud.DatabaseObject;

import javax.persistence.*;

@MappedSuperclass
public class Order extends DatabaseObject {



    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
