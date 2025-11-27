package ro.suiu.devgraph.exception;

/**
 * Exception thrown when a requested resource is not found.
 */
public class NotFoundException extends ApplicationException {

    public NotFoundException(String message) {
        super(message);
    }

}
