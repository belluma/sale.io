package capstone.backend.services;

import capstone.backend.mapper.EmployeeMapper;
import capstone.backend.repo.EmployeeRepo;
import capstone.backend.security.model.Employee;
import capstone.backend.security.model.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

import static capstone.backend.mapper.EmployeeMapper.mapEmployee;
import static capstone.backend.model.enums.UserRole.ADMIN;
import static capstone.backend.utils.EmployeeTestUtils.sampleUser;
import static capstone.backend.utils.EmployeeTestUtils.sampleUserDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class EmployeeServiceTest {

    private final EmployeeRepo repo = mock(EmployeeRepo.class);
    Supplier<UUID> uuidSupplier = mock(Supplier.class);
    private final EmployeeMapper mapper = new EmployeeMapper();
    private final EmployeeService service = new EmployeeService(repo, mapper);
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Test
    void getAllEmployees() {
        //GIVEN
        when(repo.findAll()).thenReturn(List.of(sampleUser()));
        List<EmployeeDTO> expected = List.of(mapper.mapEmployeeAndConcealData(sampleUser()));
        //WHEN
        List<EmployeeDTO> actual = service.getAllEmployees();
        //THEN
        assertIterableEquals(expected, actual);
        verify(repo).findAll();
    }

    @Test
    void createEmployee() throws NoSuchFieldException, IllegalAccessException {
        //GIVEN
        Field uuidSupplierField = EmployeeService.class.getDeclaredField("uuidSupplier");
        uuidSupplierField.setAccessible(true);
        uuidSupplierField.set(service, uuidSupplier);
        String uuid = "5211e915-c3e2-4dcb-0776-c7b900f38ab7";
        when(uuidSupplier.get()).thenReturn(UUID.fromString(uuid));
        EmployeeDTO userToSave = sampleUserDTO();
        userToSave.setUsername(uuid);
        Employee employeeWithEncryptedPassword = mapEmployee(userToSave);
        employeeWithEncryptedPassword.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        EmployeeDTO expected = mapper.mapEmployeeAndConcealData(employeeWithEncryptedPassword);
        when(repo.existsById(userToSave.getId())).thenReturn(false);
        when(repo.save(employeeWithEncryptedPassword)).thenReturn(employeeWithEncryptedPassword);
        assertThat(employeeWithEncryptedPassword, is(mapEmployee(userToSave)));
        //WHEN
        EmployeeDTO actual = service.createEmployee(userToSave);
        //THEN
        assertThat(actual, is(expected));
        verify(repo).existsById(userToSave.getId());
        verify(repo).save(mapEmployee(userToSave));
    }

    @Test
    void createEmployeeWithOnlyLastNameGiven() throws NoSuchFieldException, IllegalAccessException {
        //GIVEN
        Field uuidSupplierField = EmployeeService.class.getDeclaredField("uuidSupplier");
        uuidSupplierField.setAccessible(true);
        uuidSupplierField.set(service, uuidSupplier);
        String uuid = "5211e915-c3e2-4dcb-0776-c7b900f38ab7";
        when(uuidSupplier.get()).thenReturn(UUID.fromString(uuid));
        EmployeeDTO userToSave = sampleUserDTO();
        userToSave.setUsername(uuid);
        userToSave.setFirstName(null);
        Employee employeeWithEncryptedPassword = mapEmployee(userToSave);
        employeeWithEncryptedPassword.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        EmployeeDTO expected = mapper.mapEmployeeAndConcealData(employeeWithEncryptedPassword);
        when(repo.existsById(userToSave.getId())).thenReturn(false);
        when(repo.save(employeeWithEncryptedPassword)).thenReturn(employeeWithEncryptedPassword);
        assertThat(employeeWithEncryptedPassword, is(mapEmployee(userToSave)));
        //WHEN
        EmployeeDTO actual = service.createEmployee(userToSave);
        //THEN
        assertThat(actual, is(expected));
        verify(repo).existsById(userToSave.getId());
        verify(repo).save(mapEmployee(userToSave));
    }

    @Test
    void createEmployeeWithOnlyFirstNameGiven() throws NoSuchFieldException, IllegalAccessException {
        //GIVEN
        Field uuidSupplierField = EmployeeService.class.getDeclaredField("uuidSupplier");
        uuidSupplierField.setAccessible(true);
        uuidSupplierField.set(service, uuidSupplier);
        String uuid = "5211e915-c3e2-4dcb-0776-c7b900f38ab7";
        when(uuidSupplier.get()).thenReturn(UUID.fromString(uuid));
        EmployeeDTO userToSave = sampleUserDTO();
        userToSave.setUsername(uuid);
        userToSave.setLastName(null);
        Employee employeeWithEncryptedPassword = mapEmployee(userToSave);
        employeeWithEncryptedPassword.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        EmployeeDTO expected = mapper.mapEmployeeAndConcealData(employeeWithEncryptedPassword);
        when(repo.existsById(userToSave.getId())).thenReturn(false);
        when(repo.save(employeeWithEncryptedPassword)).thenReturn(employeeWithEncryptedPassword);
        assertThat(employeeWithEncryptedPassword, is(mapEmployee(userToSave)));
        //WHEN
        EmployeeDTO actual = service.createEmployee(userToSave);
        //THEN
        assertThat(actual, is(expected));
        verify(repo).existsById(userToSave.getId());
        verify(repo).save(mapEmployee(userToSave));
    }

    @Test
    void createEmployeeFailsWhenNoNameGiven() {
        //GIVEN
        EmployeeDTO userToSave = sampleUserDTO();
        userToSave.setFirstName(null);
        userToSave.setLastName(null);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.createEmployee(userToSave));
        assertThat(ex.getMessage(), is("At least one name attribute needs to be set!"));
    }

    @Test
    void createEmployeeFailsWhenNoPasswordGiven() {
        //GIVEN
        EmployeeDTO userToSave = sampleUserDTO();
        userToSave.setPassword(null);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.createEmployee(userToSave));
        assertThat(ex.getMessage(), is("Cannot save employee without password"));
    }

    @Test
    void deleteEmployee() {
        //GIVEN
        String username = "username";
        when(repo.existsByUsername(username)).thenReturn(true);
        //WHEN - //THEN
        service.deleteEmployee(username);
        verify(repo).removeByUsername(username);
        verify(repo).existsByUsername(username);
    }

    @Test
    void deleteEmployeeThrowsWhenUserNonExistent() {
        //GIVEN
        String username = "username";
        when(repo.existsByUsername(username)).thenReturn(false);
        //WHEN - //THEN
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.deleteEmployee(username));
        assertThat(ex.getMessage(), is(String.format("User with username %s not found", username)));
        verify(repo).existsByUsername(username);
    }

}
