package capstone.backend.security.service;

import capstone.backend.security.exceptions.InvalidCredentialsException;
import capstone.backend.security.exceptions.UserAlreadyExistsException;
import capstone.backend.security.model.Employee;
import capstone.backend.security.model.EmployeeDTO;
import capstone.backend.security.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Optional;

import static capstone.backend.mapper.EmployeeMapper.mapEmployee;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAuthServiceTest {

    EmployeeRepository repository = mock(EmployeeRepository.class);
    private final JWTUtilService jwtService = mock(JWTUtilService.class);
    private final UserAuthUtils utils = mock(UserAuthUtils.class);

    private final UserAuthService service = new UserAuthService(repository, jwtService, utils);

    @Test
    void loadUserByUsername() {
        Employee user = new Employee("username", "1234");
        when(repository.findByUsername("username")).thenReturn(Optional.of(user));
        UserDetails expected = User
                .withUsername("username")
                .password("1234")
                .authorities("user")
                .build();
        assertThat(expected, is(service.loadUserByUsername("username")));
        verify(repository).findByUsername("username");
    }

    @Test
    void loadUserByUsernameThrowsWhenUserIsNotFound() {
        when(repository.findByUsername("username")).thenReturn(Optional.empty());
        Exception ex = assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("username"));
        assertThat(ex.getMessage(), is("Username does not exist: username"));
        verify(repository).findByUsername("username");

    }


    @Test
    void signupFailsWhenUsernameAlreadyRegistered() {
        EmployeeDTO user = new EmployeeDTO("username", "1234");
        when(repository.findByUsername("username")).thenReturn(Optional.of(mapEmployee(user)));
        Exception ex = assertThrows(UserAlreadyExistsException.class, () -> service.signup(user));
        assertThat(ex.getMessage(), is("User with username username already exists"));
    }



    @Test
    void signupFailsWhenPasswordValidationFails() throws InvalidCredentialsException {
        EmployeeDTO user = new EmployeeDTO("username", "1234");
        when(repository.findByUsername("username")).thenReturn(Optional.empty());
        doThrow(new InvalidCredentialsException("Invalid password")).when(utils).validatePassword("1234");
        Exception ex = assertThrows(InvalidCredentialsException.class, () -> service.signup(user));
        assertThat(ex.getMessage(), is("Invalid password"));
    }
}
