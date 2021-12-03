package capstone.backend.services;

import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.mapper.SupplierMapper;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.db.order.OrderToSupplier;
import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.repo.SupplierRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SupplierService {

    private final SupplierRepo repo;
    private static final String NO_SUPPLIER = "Couldn't find a supplier with the id %d";

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
                .orElseThrow(() -> new EntityNotFoundException(String.format(NO_SUPPLIER, id)));
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
            throw new EntityNotFoundException(String.format(NO_SUPPLIER, supplier.getId()));
        }
        return SupplierMapper.mapSupplier(repo
                .save(SupplierMapper.mapSupplier(supplier)));
    }

    public boolean supplierExists(Long id) {
        return repo.existsById(id);
    }

    public void updateProductList(Product product) throws IllegalArgumentException {
        Set<Supplier> suppliers = product.getSuppliers();
        suppliers.forEach(supplier -> {
            if (!repo.existsById(supplier.getId()))
                throw (new IllegalArgumentException("You're trying to associate a product to a supplier that does not exist!"));
        });
        suppliers.forEach(supplier -> {
            supplier.updateProductList(product);
            repo.save(supplier);
        });
    }

    public void updateOrderList(OrderToSupplier order) throws IllegalArgumentException {
        Supplier supplier = order.getSupplier();
        if (!repo.existsById(supplier.getId()))
            throw (new IllegalArgumentException("You're trying to associate a order to a supplier that does not exist!"));
        supplier.updateOrderList(order);
        repo.save(supplier);
    }
}



