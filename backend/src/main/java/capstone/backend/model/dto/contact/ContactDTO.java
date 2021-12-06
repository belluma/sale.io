package capstone.backend.model.dto.contact;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ContactDTO  {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String picture;
}
