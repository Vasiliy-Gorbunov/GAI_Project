package test.gai.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String description) {
        super(description);
    }
}
