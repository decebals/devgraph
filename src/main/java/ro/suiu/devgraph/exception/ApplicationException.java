package ro.suiu.devgraph.exception;

/**
 * Base class for application-specific exceptions.
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

}
