package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Ksiazka o takim id nie istnieje")
public class BookNoAlreadyExistsException extends RuntimeException{
    public BookNoAlreadyExistsException() {
        super("Ksiazka o takim id nie istnieje");
    }
}
