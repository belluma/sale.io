package capstone.backend.security.service;

import capstone.backend.repo.EmployeeRepo;
import capstone.backend.security.exceptions.InvalidCredentialsException;
import capstone.backend.security.exceptions.UserAlreadyExistsException;
import capstone.backend.security.model.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

import static capstone.backend.mapper.EmployeeMapper.mapEmployee;


@Service
public class UserAuthService implements UserDetailsService {

    private final EmployeeRepo repository;
    private final JWTUtilService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserAuthUtils utils;


    @Autowired
    public UserAuthService(EmployeeRepo repository, JWTUtilService jwtService, UserAuthUtils utils) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.utils = utils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(appUser -> User
                        .withUsername(username)
                        .password(appUser.getPassword())
                        .authorities("user")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Username does not exist: " + username));
    }

    public String signup(EmployeeDTO user) throws InvalidCredentialsException, IllegalArgumentException {
        validateUserData(user);
        if (repository.findByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUsername(String.valueOf(UUID.randomUUID()));
            repository.save(mapEmployee(user));
            return jwtService.createToken(new HashMap<>(), user.getUsername());
        }
        throw new UserAlreadyExistsException(String.format("User with username %s already exists", user.getUsername()));
    }

    private void validateUserData(EmployeeDTO user) throws InvalidCredentialsException {
        utils.validatePassword(user.getPassword());
    }

}
