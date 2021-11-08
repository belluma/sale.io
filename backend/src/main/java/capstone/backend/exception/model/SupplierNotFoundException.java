package capstone.backend.exception.model;

import java.util.NoSuchElementException;

public class SupplierNotFoundException extends NoSuchElementException {

    public SupplierNotFoundException(String message){
        super(message);
    }
}
