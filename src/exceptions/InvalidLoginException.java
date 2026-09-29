package exceptions;

/**
 * Thrown when a user attempts to log in with an unknown ID
 * or an incorrect password.
 */
public class InvalidLoginException extends Exception {
    public InvalidLoginException(String message) {
        super(message);
    }
}
