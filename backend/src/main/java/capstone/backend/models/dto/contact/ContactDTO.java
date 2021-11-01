package capstone.backend.models.dto.contact;


import capstone.backend.crud.DataTransferObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ContactDTO extends DataTransferObject {

    private Long id;

    private String name;
    private String email;
    private String phone;
    //TODO what's the best way to store and access files in Db?
    private String picture;
}
