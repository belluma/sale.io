package capstone.backend.exception.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.NoSuchElementException;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityNotFoundException extends NoSuchElementException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
