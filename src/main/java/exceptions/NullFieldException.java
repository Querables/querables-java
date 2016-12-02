package exceptions;

public class NullFieldException extends RuntimeException {
    public NullFieldException(Object key) {
        super("Fields cannot be null! Key: " + key.toString());
    }
}
