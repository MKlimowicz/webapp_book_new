package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User o takim id nie istnieje")
public class UserNoAlreadyExistsException extends RuntimeException{
    public UserNoAlreadyExistsException() {
        super("User o takim id nie istnieje");
    }
}
