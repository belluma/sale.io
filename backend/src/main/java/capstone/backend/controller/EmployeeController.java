package capstone.backend.controller;

import capstone.backend.security.model.Employee;
import capstone.backend.services.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("s")
    public List<Employee> getAllEmployees(){
    return service.getAllEmployees();
}


}
