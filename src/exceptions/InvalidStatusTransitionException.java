package exceptions;

/**
 * Thrown when an item's status is changed in a way that violates
 * the allowed lifecycle (e.g. changing status after it has already
 * been RETURNED, or marking something RETURNED without a valid prior state).
 */
public class InvalidStatusTransitionException extends Exception {
    public InvalidStatusTransitionException(String message) {
        super(message);
    }
}
