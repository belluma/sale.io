package capstone.backend.controller;

import capstone.backend.model.CustomError;
import capstone.backend.model.exception.ProductIdAlreadyTakenException;
import capstone.backend.model.exception.ProductNotFoundException;
import capstone.backend.security.exceptions.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, AuthenticationException.class, UserAlreadyExistsException.class, ProductIdAlreadyTakenException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex) {
        CustomError message = new CustomError(ex);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({NoSuchElementException.class, ProductNotFoundException.class})
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex) {
        CustomError message = new CustomError(ex);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadCredentialsException.class, AccessDeniedException.class, HttpClientErrorException.class})
    public ResponseEntity<Object> handleBadCredentialsException(Exception ex) {
        CustomError message = new CustomError(ex);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleAllTheRest(Exception ex) {
        CustomError message = new CustomError(ex);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
