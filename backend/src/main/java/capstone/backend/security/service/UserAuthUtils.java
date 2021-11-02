package capstone.backend.security.service;

import capstone.backend.security.exceptions.InvalidCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class UserAuthUtils {
    //TODO implement password validation
    public void validatePassword(String password) throws InvalidCredentialsException {
    }
    //TODO implement username validation
    public void validateUsername(String username)throws InvalidCredentialsException  {
    }
}
