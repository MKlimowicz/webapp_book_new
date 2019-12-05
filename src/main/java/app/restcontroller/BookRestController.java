package app.restcontroller;


import app.dto.BookBorrowDto;
import app.dto.BookDto;
import app.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookRestController {

    private BookServices bookServices;

    @Autowired
    public BookRestController(BookServices bookServices) {
        this.bookServices = bookServices;
    }


    @GetMapping()
    public List<BookDto> getListBooks() {
        return bookServices.getListBook();
    }

    @PostMapping()
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDto) {
        if(bookDto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ksiązka która próbujesz zapisaćm, nie moze mięc ustawionego id");
        BookDto savedBook = bookServices.save(bookDto);
        System.out.println("REST | Udało się zapisać ksiązke -> " + savedBook);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Integer id, @RequestBody BookDto dto){
        if(!id.equals(dto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi miec takie samo id jak id w ścieżce");
        BookDto updateDto = bookServices.save(dto);
        return ResponseEntity.ok(updateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable Integer id) {
        if(id == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Musisz podać id ksiązki ktora chcesz usuanac");
        BookDto deletedDto = bookServices.delete(id);
        return ResponseEntity.ok(deletedDto);
    }

    @GetMapping("/{id}/borrowBook")
    public List<BookBorrowDto> getUserWhoBorrowBookById(@PathVariable Integer id) {
        return bookServices.findAllUserWhoBorrowBook(id);
    }
}
