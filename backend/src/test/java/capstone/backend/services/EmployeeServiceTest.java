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
import java.util.List;

import static capstone.backend.mapper.EmployeeMapper.mapEmployee;
import static capstone.backend.utils.EmployeeTestUtils.sampleUser;
import static capstone.backend.utils.EmployeeTestUtils.sampleUserDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class EmployeeServiceTest {

    private final EmployeeRepo repo = mock(EmployeeRepo.class);
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
    void createEmployee() {
        //GIVEN
        EmployeeDTO userToSave = sampleUserDTO();
        Employee employeeWithEncryptedPassword = sampleUser().withPassword(passwordEncoder.encode(userToSave.getPassword()));
        EmployeeDTO expected = mapper.mapEmployeeAndConcealData(employeeWithEncryptedPassword);
        when(repo.save(employeeWithEncryptedPassword)).thenReturn(employeeWithEncryptedPassword);
        //WHEN
        EmployeeDTO actual = service.createEmployee(userToSave);
        //THEN
        assertThat(actual, is(expected));
        verify(repo).save(mapEmployee(userToSave));
    }

    @Test
    void createEmployeeWhenNoNameGiven() {
        //GIVEN
        EmployeeDTO userToSave = sampleUserDTO();
        userToSave.setFirstName(null);
        userToSave.setLastName(null);
        //WHEN - //THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.createEmployee(userToSave));
        assertThat(ex.getMessage(), is("At least one name attribute needs to be set!"));
    }

    @Test
    void createEmployeeWhenNoPasswordGiven() {
        //GIVEN
        EmployeeDTO userToSave = sampleUserDTO().withPassword(null);
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
        verify(repo).deleteByUsername(username);
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
