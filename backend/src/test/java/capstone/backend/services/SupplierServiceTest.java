package capstone.backend.services;

import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.model.enums.Weekday;
import capstone.backend.repo.SupplierRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static capstone.backend.mapper.SupplierMapper.mapSupplier;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplierDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class SupplierServiceTest {

    private final SupplierRepo repo = mock(SupplierRepo.class);
    private final SupplierService service = new SupplierService(repo);

    @Test
    void getAllSuppliersWithDetails() {
        //GIVEN
        when(repo.findAll()).thenReturn(List.of(sampleSupplier()));
        List<SupplierDTO> expected = List.of(sampleSupplierDTO());
        //WHEN
        List<SupplierDTO> actual = service.getAllSuppliersWithDetails();
        //THEN
        assertIterableEquals(actual, expected);
        verify(repo).findAll();
    }

    @Test
    void getSupplierDetails() {
        //GIVEN
        when(repo.findById(123L)).thenReturn(Optional.of(sampleSupplier()));
        SupplierDTO expected = sampleSupplierDTO();
        //WHEN
        SupplierDTO actual = service.getSupplierDetails(123L);
        //THEN
        assertThat(actual, is(expected));
        verify(repo).findById(123L);
    }

    @Test
    void getSupplierDetailsThrowsWhenNoSupplierFound(){
        //GIVEN
        when(repo.findById(123L)).thenReturn(Optional.empty());
        //WHEN - THEN
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.getSupplierDetails(123L));
        assertThat(ex.getMessage(), is("Couldn't find a supplier with the id 123"));
        verify(repo).findById(123L);
        }
    @Test
    void createSupplier() {
        //GIVEN
        Supplier supplierToSave = sampleSupplier();
        when(repo.save(supplierToSave)).thenReturn(supplierToSave);
        SupplierDTO expected = sampleSupplierDTO();
        //WHEN
        SupplierDTO actual = service.createSupplier(sampleSupplierDTO());
        //THEN
        assertThat(actual, is(expected));
        verify(repo).save(supplierToSave);
    }

    @Test
    void createSupplierThrowsWhenProductIdAlreadyTaken() {
        //GIVEN
        Supplier supplier = sampleSupplier();
        SupplierDTO supplierThatAlreadyExists = sampleSupplierDTO();
        when(repo.findById(123L)).thenReturn(Optional.of(supplier));
        Exception ex = assertThrows(EntityWithThisIdAlreadyExistException.class, () -> service.createSupplier(supplierThatAlreadyExists));
        //THEN
        assertThat(ex.getMessage(), is(String.format("Supplier %s already has the id %d", supplier.getFirstName(), supplier.getId())));
        verify(repo).findById(123L);
    }


    @Test
    void editSupplier() {
        //GIVEN
        Supplier supplier = sampleSupplier();
        Supplier editedSupplier = sampleSupplier();
        editedSupplier.setOrderDay(Weekday.MONDAY);
        when(repo.findById(123L)).thenReturn(Optional.of(supplier));
        when(repo.save(editedSupplier)).thenReturn(editedSupplier);
        //WHEN
        SupplierDTO expected = sampleSupplierDTO();
        expected.setOrderDay(Weekday.MONDAY);
        //THEN
        assertThat(expected, is(service.editSupplier(mapSupplier(editedSupplier))));
        verify(repo).findById(123L);
        verify(repo).save(editedSupplier);
    }

    @Test
    void editSupplierThrowsWhenProductNonExistent() {
        //GIVEN
        Supplier supplier = sampleSupplier();
        SupplierDTO supplierThatDoesNotCarryProduct = sampleSupplierDTO();
        when(repo.findById(123L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.editSupplier(supplierThatDoesNotCarryProduct));
        //THEN
        assertThat(ex.getMessage(), is(String.format("Couldn't find a supplier with the id %d", supplier.getId())));
        verify(repo).findById(123L);
    }

}
