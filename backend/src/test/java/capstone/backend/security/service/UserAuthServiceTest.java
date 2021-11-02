package capstone.backend.security.service;

import capstone.backend.security.exceptions.InvalidCredentialsException;
import capstone.backend.security.exceptions.UserAlreadyExistsException;
import capstone.backend.security.model.AppUser;
import capstone.backend.security.model.UserDTO;
import capstone.backend.security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAuthServiceTest {

    UserRepository repository = mock(UserRepository.class);
    private final JWTUtilService jwtService = mock(JWTUtilService.class);
    private final UserMapper mapper = new UserMapper();
    private final UserAuthUtils utils = mock(UserAuthUtils.class);

    private final UserAuthService service = new UserAuthService(repository, jwtService, utils);

    @Test
    void loadUserByUsername() {
        AppUser user = new AppUser("username", "1234");
        when(repository.findById("username")).thenReturn(Optional.of(user));
        UserDetails expected = User
                .withUsername("username")
                .password("1234")
                .authorities("user")
                .build();
        assertThat(expected, is(service.loadUserByUsername("username")));
        verify(repository).findById("username");
    }

    @Test
    void loadUserByUsernameThrowsWhenUserIsNotFound() throws Exception {
        when(repository.findById("username")).thenReturn(Optional.empty());
        Exception ex = assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("username"));
        assertThat(ex.getMessage(), is("Username does not exist: username"));
        verify(repository).findById("username");

    }

    @Test
    void signup() throws InvalidCredentialsException {
        UserDTO user = new UserDTO("username", "1234");
        when(repository.findById("username")).thenReturn(Optional.empty());
        when(jwtService.createToken(new HashMap<>(), "username")).thenReturn("valid.jwt.token");
        String expected = "valid.jwt.token";
        assertThat(expected, is(service.signup(user)));
        verify(repository).findById("username");
    }

    @Test
    void signupFailsWhenUsernameAlreadyRegistered() {
        UserDTO user = new UserDTO("username", "1234");
        when(repository.findById("username")).thenReturn(Optional.of(mapper.mapUser(user)));
        Exception ex = assertThrows(UserAlreadyExistsException.class, () -> service.signup(user));
        assertThat(ex.getMessage(), is("User with username username already exists"));
    }

    @Test
    void signupFailsWhenUsernameValidationFails() throws InvalidCredentialsException {
        UserDTO user = new UserDTO("username", "1234");
        when(repository.findById("username")).thenReturn(Optional.empty());
        doThrow(new InvalidCredentialsException("Invalid username")).when(utils).validateUsername("username");
        Exception ex = assertThrows(InvalidCredentialsException.class, () -> service.signup(user));
        assertThat(ex.getMessage(), is("Invalid username"));
    }

    @Test
    void signupFailsWhenPasswordValidationFails() throws InvalidCredentialsException {
        UserDTO user = new UserDTO("username", "1234");
        when(repository.findById("username")).thenReturn(Optional.empty());
        doThrow(new InvalidCredentialsException("Invalid password")).when(utils).validatePassword("1234");
        Exception ex = assertThrows(InvalidCredentialsException.class, () -> service.signup(user));
        assertThat(ex.getMessage(), is("Invalid password"));
    }
}
