package capstone.backend.services;

import capstone.backend.exception.model.EntityNotFoundException;
import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.mapper.SupplierMapper;
import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.repo.SupplierRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepo repo;

    public SupplierService(SupplierRepo repo) {
        this.repo = repo;
    }

    public List<SupplierDTO> getAllSuppliersWithDetails() {
        return repo.findAll()
                .stream()
                .map(SupplierMapper::mapSupplier)
                .toList();
    }

    public SupplierDTO getSupplierDetails(Long id) {
        return repo
                .findById(id)
                .map(SupplierMapper::mapSupplier)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find a supplier with the id %d", id)));
    }

    public SupplierDTO createSupplier(SupplierDTO supplier) {
        if (supplier.getId() != null && repo
                .findById(supplier.getId())
                .isPresent()) {
            throw new EntityWithThisIdAlreadyExistException(String.format("Supplier %s already has the id %d", supplier.getFirstName(), supplier.getId()));
        }
        return SupplierMapper
                .mapSupplier(repo
                        .save(SupplierMapper
                                .mapSupplier(supplier)));
    }

    public SupplierDTO editSupplier(SupplierDTO supplier) {
        if (repo
                .findById(supplier.getId())
                .isEmpty()) {
            throw new EntityNotFoundException(String.format("Couldn't find a supplier with the id %d", supplier.getId()));
        }
        return SupplierMapper.mapSupplier(repo
                .save(SupplierMapper.mapSupplier(supplier)));
    }

}



