package capstone.backend.services.mapper;

import capstone.backend.crud.CrudMapper;
import capstone.backend.crud.DataTransferObject;
import capstone.backend.crud.DatabaseObject;
import capstone.backend.models.db.contact.Supplier;
import capstone.backend.models.db.contact.Customer;
import capstone.backend.models.dto.contact.CustomerDTO;
import capstone.backend.models.dto.contact.SupplierDTO;

public class SupplierMapper implements CrudMapper<SupplierDTO, Supplier> {
    @Override
    public SupplierDTO mapToDto(DatabaseObject databaseObject) {
        return new SupplierDTO();
    }

    @Override
    public Supplier mapToDbo(DataTransferObject databaseObject) {
        return new Supplier();    }
}
