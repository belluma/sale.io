package capstone.backend.models.db;


import capstone.backend.crud.DatabaseObject;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends DatabaseObject {


    @OneToMany
    @ToString.Exclude
    private List<Product> products;


}
