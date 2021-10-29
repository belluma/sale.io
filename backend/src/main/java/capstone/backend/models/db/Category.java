package capstone.backend.models.db;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Category {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany
    @ToString.Exclude
    private List<Product> products;
}
