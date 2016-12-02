package exceptions;

public class NullKeyTypeException extends RuntimeException {
    public NullKeyTypeException() {
        super("Key type cannot be null!");
    }
}
