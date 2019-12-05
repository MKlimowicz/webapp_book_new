package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Magazyn o takim tytule już istnieje")
public class MagazineAlreadyExistsException extends RuntimeException{
    public MagazineAlreadyExistsException() {
        super("Magazyn o takim tytule już istnieje");
    }
}
