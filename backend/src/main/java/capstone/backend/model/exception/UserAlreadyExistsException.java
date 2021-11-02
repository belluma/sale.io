package capstone.backend.model.exception;

import javax.naming.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {
    public UserAlreadyExistsException(){
        super();}

    public UserAlreadyExistsException(final String message){
        super(message);
    }
}
