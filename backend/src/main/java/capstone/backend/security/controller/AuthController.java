package capstone.backend.security.controller;

import capstone.backend.models.dto.EmployeeDTO;
import capstone.backend.security.service.JWTUtilService;
import capstone.backend.security.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtilService jwtService;
    private final UserAuthService userAuthService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTUtilService jwtService, UserAuthService userAuthService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userAuthService = userAuthService;
    }

//    @PostMapping("/login")
//    public String login(@RequestBody EmployeeDTO employee){
//        this.authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(employee.getName(), employee.getPassword()));
//        return jwtService.createToken(new HashMap<>(), employee.getName());
//    }

    @PostMapping("/signup")
    public String signup(@RequestBody EmployeeDTO employee)throws IllegalArgumentException, AuthenticationException {
        return userAuthService.signup(employee);
    }
}
