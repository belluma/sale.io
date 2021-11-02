package capstone.backend.model.dto.contact;


import capstone.backend.crud.DataTransferObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ContactDTO extends DataTransferObject {

    private Long id;

    private String name;
    private String email;
    private String phone;
    private String picture;
}
