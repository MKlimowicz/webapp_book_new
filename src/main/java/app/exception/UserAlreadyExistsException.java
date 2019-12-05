package app.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Uzytkownik o takim pesel juz jest w bazie")
public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
        super("Uzytkownik o takim peselu już istnieje");
    }
}
