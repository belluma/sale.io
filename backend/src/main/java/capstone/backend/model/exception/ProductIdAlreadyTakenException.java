package capstone.backend.model.exception;

public class ProductIdAlreadyTakenException extends RuntimeException {
    public ProductIdAlreadyTakenException(String message) {
        super(message);
    }
}
