package app.exception;

public class InvalidBorrowPublication extends RuntimeException{
    public InvalidBorrowPublication(String message) {
        super(message);
    }
}
