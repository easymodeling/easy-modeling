package io.github.easymodeling.processor;

public class BasicTypeModelerException extends RuntimeException {

    public BasicTypeModelerException(String type) {
        super("Cannot generate modeler for type " + type);
    }
}
