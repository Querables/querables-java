package io.github.querables.exceptions;

public class NoGettersInKeyException extends RuntimeException {
    public NoGettersInKeyException(Class keyType) {
        super("There is no available getters in key of type: " + keyType.getName());
    }
}
