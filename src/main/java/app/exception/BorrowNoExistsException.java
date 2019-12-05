package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Nie istnieje takie wyporzyczenie")
public class BorrowNoExistsException extends RuntimeException{
    public BorrowNoExistsException() {
        super("Nie istnieje takie wyporzyczenie");
    }
}
