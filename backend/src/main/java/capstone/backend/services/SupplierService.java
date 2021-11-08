package capstone.backend.services;

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
        return List.of();
    }

    public SupplierDTO getSupplierDetails(Long id) {
        return SupplierDTO.builder().build();
    }

    public SupplierDTO createSupplier(SupplierDTO supplier) {

        return SupplierDTO.builder().build();
    }

    public SupplierDTO editSupplier(SupplierDTO supplier) {
        return SupplierDTO.builder().build();

    }


}
