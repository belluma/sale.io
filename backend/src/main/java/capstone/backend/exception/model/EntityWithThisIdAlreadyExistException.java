package capstone.backend.exception.model;

public class EntityWithThisIdAlreadyExistException extends RuntimeException {
    public EntityWithThisIdAlreadyExistException(String message) {
        super(message);
    }
}
