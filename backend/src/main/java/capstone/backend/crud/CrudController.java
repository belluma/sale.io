package capstone.backend.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forbidden")
public class CrudController<DTO extends DataTransferObject, DBO extends DatabaseObject> {


    private final CrudService<DTO, DBO> service;

    @Autowired
    public CrudController(CrudService<DTO, DBO> service, CrudMapper<DTO, DBO> mapper ){
        this.service = service;
        this.service.setMapper(mapper);
    }
    @GetMapping()
    public List<DTO> getAll(){
        return service.getAll();
    }

    @PostMapping()
    public DTO create(@RequestBody DTO dtoToSave){
        return service.save(dtoToSave);
    }
}
