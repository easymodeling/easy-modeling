package io.github.easymodeling;

public class BasicTypeModelerException extends RuntimeException {

    public BasicTypeModelerException(String type) {
        super("Cannot generate modeler for type " + type);
    }
}
