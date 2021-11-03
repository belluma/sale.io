package capstone.backend.security.service;

import capstone.backend.security.exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthUtilsTest {

    private final UserAuthUtils utils = new UserAuthUtils();

    @Test
    void validatePasswordThrowsWhenPasswordTooShort() {
        assertThrows(InvalidCredentialsException.class, () -> utils.validatePassword("ab"));
    }

    @Test
    void validatePasswordDoesNotThrowWhithValidPassword() {
        try {
            utils.validatePassword("abc123!");
        } catch (InvalidCredentialsException e) {
            fail();
        }
    }




}
