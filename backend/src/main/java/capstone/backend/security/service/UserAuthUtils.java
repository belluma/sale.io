package capstone.backend.security.service;

import capstone.backend.security.exceptions.InvalidCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class UserAuthUtils {
    public void validatePassword(String password) throws InvalidCredentialsException {
        if(password.length() < 3) throw new InvalidCredentialsException("Password too short");
    }

}
