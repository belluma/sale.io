package capstone.backend.services;

import capstone.backend.repo.SupplierRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
class SupplierServiceTest {

    private final SupplierRepo repo = mock(SupplierRepo.class);
    private final SupplierService service = new SupplierService(repo);

    @Test
    void getAllSuppliersWithDetails() {
        //GIVEN
        when repo
        //WHEN

        //THEN
    }

    @Test
    void getSupplierDetails() {
    }

    @Test
    void createSupplier() {
    }

    @Test
    void editSupplier() {
    }
}
