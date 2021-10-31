package capstone.backend.security.service;

import capstone.backend.models.dto.contact.EmployeeDTO;
import capstone.backend.models.exception.UserAlreadyExistsException;
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
public class UserAuthService implements UserDetailsService  {

    private final EmployeeRepository repository;
    private final JWTUtilService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmployeeMapper mapper;
    private final EmployeeAuthUtils utils;


    @Autowired
    public UserAuthService(EmployeeRepository repository, JWTUtilService jwtService, EmployeeMapper mapper, EmployeeAuthUtils utils) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.mapper = mapper;
        this.utils = utils;
    }
//TODO  make this right - do I even need this method? can i somehow not implement UserDetailsService
    public UserDetails loadUserByUsername(String username){
        return User.withUsername("a").password("b").authorities("c").build();
    }
    public UserDetails loadUserId(Long id) throws UsernameNotFoundException {
        return repository.findById(id)
                .map(employee -> User
                        .withUsername(employee.getName())
                        .password(employee.getPassword())
                        .authorities("user")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find a user with id " + id));
    }

    public String signup(EmployeeDTO employee) throws AuthenticationException {
        validateUserData(employee);
        if (repository.findById(employee.getId()).isEmpty()) {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            repository.save(mapper.mapEmployee(employee));
            return jwtService.createToken(new HashMap<>(), employee.getName());
        }
        throw new UserAlreadyExistsException(String.format("User with username %s already exists", employee.getName()));
    }

    private void validateUserData(EmployeeDTO employee) throws IllegalArgumentException {
        utils.validatePassword(employee.getPassword());
        utils.validateName(employee.getName());
    }

}
