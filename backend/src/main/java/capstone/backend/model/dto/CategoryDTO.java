package capstone.backend.model.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class CategoryDTO {

    private Long id;
    private String name;
}
