package exceptions;

public class NullKeyException extends RuntimeException {
    public NullKeyException() {
        super("Key cannot be null!");
    }
}
