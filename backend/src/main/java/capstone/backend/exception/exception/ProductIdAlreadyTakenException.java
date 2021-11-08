package capstone.backend.exception.exception;

public class ProductIdAlreadyTakenException extends RuntimeException {
    public ProductIdAlreadyTakenException(String message) {
        super(message);
    }
}
