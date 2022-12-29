package io.github.easymodeling;

public class EasyModelingException extends RuntimeException {

    public EasyModelingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyModelingException(String message) {
        super(message);
    }
}
