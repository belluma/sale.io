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


    @Test
    void getAllEmployees() {
        //GIVEN
        when(repo.findAll()).thenReturn(List.of(EmployeeTestUtils.sampleUser()));
        List<EmployeeDTO> actual = service.getAllEmployees();
       //WHEN
        List<EmployeeDTO> expected = List.of(mapper.mapEmployeeAndConcealData(EmployeeTestUtils.sampleUser()));
        //THEN
        assertIterableEquals( expected, actual);
        verify(repo).findAll();
    }



}
