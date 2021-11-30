package capstone.backend.model.dto;

import lombok.*;

import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class CategoryDTO {

    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO category = (CategoryDTO) o;
        return Objects.equals(name, category.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
