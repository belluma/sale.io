package capstone.backend.controller;


import capstone.backend.crud.CrudController;
import capstone.backend.crud.CrudMapper;
import capstone.backend.crud.CrudService;
import capstone.backend.models.db.contact.Supplier;
import capstone.backend.models.dto.contact.SupplierDTO;
import capstone.backend.services.mapper.SupplierMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("supplier")
public class SupplierController extends CrudController<SupplierDTO, Supplier> {


    public SupplierController(CrudService<SupplierDTO, Supplier> service) {
        super(service, new SupplierMapper());
    }
}
