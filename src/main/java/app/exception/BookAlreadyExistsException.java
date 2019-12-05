package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Ksiazka o takim numerze isbn istnieje")
public class BookAlreadyExistsException extends RuntimeException{
    public BookAlreadyExistsException() {
        super("Ksiazka o takim numerze isbn istnieje");
    }
}
