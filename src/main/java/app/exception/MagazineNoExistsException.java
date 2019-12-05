package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Magazyn o takim id nie istnieje")
public class MagazineNoExistsException extends RuntimeException{
    public MagazineNoExistsException() {
        super("Magazyn o takim id nie istnieje");
    }
}
