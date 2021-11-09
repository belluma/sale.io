package capstone.backend.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Data
public class CustomError {
    private LocalDateTime timeStamp;
    private String message;
    private Exception exceptionTrail;

    public CustomError(Exception ex) {
        this.timeStamp = LocalDateTime.now();
        String errorMessage = ex.getMessage();
        if(errorMessage == null) errorMessage = ex.toString();
        this.message = errorMessage;
        log.error(this.message, ex);
    }
}
