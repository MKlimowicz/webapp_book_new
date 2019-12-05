package app.restcontroller;


import app.dto.BorrowDto;
import app.exception.InvalidBorrowPublication;
import app.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/borrow")
public class BorrowRestController {

    private BorrowService borrowService;

    @Autowired
    public BorrowRestController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }


    @PostMapping()
    public ResponseEntity<BorrowDto> createBorrow(@RequestBody BorrowDto dto) {
        BorrowDto borrowDto = null;
        try {

            if(dto.getId() != null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID BORROWDTO NIE MOZE BYC USTAWIONE");

            borrowDto = borrowService.saveBorrow(dto.getUser_id(), dto.getPublication_id());

        } catch (InvalidBorrowPublication e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        URI location = createURI(borrowDto.getId());
        return ResponseEntity.created(location).body(borrowDto);
    }

    private URI createURI(Integer borrow_id) {
        return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/{id}")
                    .buildAndExpand(borrow_id)
                    .toUri();
    }


    @PostMapping("/{id}/end")
    public ResponseEntity returnBorrow(@PathVariable Integer id) {
        LocalDate date = borrowService.returnBorrow(id);
        return ResponseEntity.ok(date);
    }
}
