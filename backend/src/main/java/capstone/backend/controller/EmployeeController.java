package capstone.backend.controller;

import capstone.backend.security.model.EmployeeDTO;
import capstone.backend.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;


    @GetMapping()
    public List<EmployeeDTO> getAllEmployees(){
    return service.getAllEmployees();
}


}
