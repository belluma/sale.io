package capstone.backend.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.NoSuchElementException;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductNotFoundException extends NoSuchElementException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
