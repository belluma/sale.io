package capstone.backend.model.dto.contact;


import lombok.Data;

@Data
public abstract class ContactDTO  {

    private Long id;

    private String name;
    private String email;
    private String phone;
    private String picture;
}
