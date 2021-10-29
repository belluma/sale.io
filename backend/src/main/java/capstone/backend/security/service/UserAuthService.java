package capstone.backend.security.service;

import capstone.backend.models.db.Employee;
import capstone.backend.models.dto.EmployeeDTO;
import capstone.backend.security.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashMap;


@Service
public class UserAuthService implements UserDetailsService {

    private final EmployeeRepository repository;
    private final JWTUtilService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmployeeMapper mapper = new EmployeeMapper();
    private final EmployeeAuthUtils utils = new EmployeeAuthUtils();


    @Autowired
    public UserAuthService(EmployeeRepository repository, JWTUtilService jwtService, EmployeeAuthUtils utils) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.utils = utils;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return repository.findById(id)
                .map(employee -> User
                        .withUsername(employee.getName())
                        .password(employee.getPassword())
                        .authorities("user")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find a user wiht id " + id));
    }

    public String signup(EmployeeDTO employee) throws AuthenticationException, IllegalArgumentException {
        validateUserData(employee);
        if (repository.findById(employee.getName()).isEmpty()) {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            repository.save(mapper.mapUser(employee));
            return jwtService.createToken(new HashMap<>(), employee.getName());
        }
        throw new UserAlreadyExistsException(String.format("User with username %s already exists", employee.getName()));
    }

    private void validateUserData(EmployeeDTO employee) throws IllegalArgumentException {
        utils.validatePassword(employee.getPassword());
        utils.validateName(employee.getName());
    }

}
