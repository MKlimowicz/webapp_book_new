package app.restcontroller;


import app.dto.UserBorrowDto;
import app.dto.UserDto;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @PostMapping()
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        if(userDto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uzytkownik ktorego probujesz zapisac nie moze miec ustawionego id");
        UserDto savedDto = userService.save(userDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id")Integer id,
                                          @RequestBody UserDto userDto){
        if(!id.equals(userDto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id z patha musi byc takie samo jako id obiektu");
        UserDto updatedUser = userService.update(userDto);
        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable("id")Integer id) {
        if(id == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id uzytkownika którego chcesz usunac musi byc rozne od null");
        UserDto delete = userService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @GetMapping("/{id}/userBorrow")
    public List<UserBorrowDto> getAllBookWhatUserBorrow(@PathVariable Integer id) {
        return userService.findBorrowPublication(id);
    }


}
