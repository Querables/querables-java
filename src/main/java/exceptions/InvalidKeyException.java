package exceptions;

public class InvalidKeyException extends RuntimeException {
    public InvalidKeyException(Class expected, Class actual) {
        super("Invalid key! Expected: " + expected.getName() + ", but was: " + actual.getName());
    }
}
