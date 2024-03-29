package capstone.backend.controller;

import capstone.backend.security.model.EmployeeDTO;
import capstone.backend.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;


    @GetMapping()
    public List<EmployeeDTO> getAllEmployees() {
        return service.getAllEmployees();
    }

    @PostMapping()
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employee) {
        return service.createEmployee(employee);
    }

    @DeleteMapping("{username}")
    public void deleteEmployee(@PathVariable String username) {
        service.deleteEmployee(username);
    }

}
