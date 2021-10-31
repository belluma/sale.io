package capstone.backend.models.dto.contact;


import lombok.Data;

@Data
public abstract class ContactDTO {

    private Long id;

    private String name;
    private String email;
    private String phone;
    //TODO what's the best way to store and access files in Db?
    private String picture;
}
