package com.github.querables.exceptions;

public class InvalidKeyTypeException extends RuntimeException {
    public InvalidKeyTypeException(Class expected, Class actual) {
        super("Invalid key! Expected: " + expected.getName() + ", but was: " + actual.getName());
    }
}
