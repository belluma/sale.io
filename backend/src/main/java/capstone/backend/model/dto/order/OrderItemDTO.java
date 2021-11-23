package capstone.backend.model.dto.order;

import capstone.backend.model.dto.ProductDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class OrderItemDTO {

    private Long id;
    private ProductDTO product;
    private int quantity;

}
