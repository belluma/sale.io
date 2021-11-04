package capstone.backend.services;

import capstone.backend.mapper.EmployeeMapper;
import capstone.backend.repo.EmployeeRepo;
import capstone.backend.security.model.EmployeeDTO;
import capstone.backend.utils.EmployeeTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class EmployeeServiceTest {

    private final EmployeeRepo repo = mock(EmployeeRepo.class);
    private final EmployeeMapper mapper = new EmployeeMapper();
    private final EmployeeService service = new EmployeeService(repo, mapper);
    private final EmployeeTestUtils utils = new EmployeeTestUtils();


    @Test
    void getAllEmployees() {
        when(repo.findAll()).thenReturn(List.of(utils.sampleUser()));
        List<EmployeeDTO> actual = service.getAllEmployees();
        List<EmployeeDTO> expected = List.of(mapper.mapEmployeeAndConcealData(utils.sampleUser()));
        assertIterableEquals( expected, actual);
        verify(repo).findAll();
    }



}
