package model.exceptions;

public class FileException extends DomainException {

    public FileException(String message) {

        super(message);
    }
    public FileException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
