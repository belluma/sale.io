package capstone.backend.crud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CrudController<DTO extends DataTransferObject, DBO extends DatabaseObject> {

    CrudService<DTO, DBO> service;

    public CrudController(CrudService<DTO, DBO> service){
        this.service = service;
    }
    @GetMapping("")
    public List<DTO> getAll(){
        return service.getAll();
    }
}
