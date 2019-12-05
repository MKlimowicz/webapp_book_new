package app.services;

import app.dto.BookBorrowDto;
import app.dto.BookDto;
import app.exception.BookAlreadyExistsException;
import app.exception.BookNoAlreadyExistsException;
import app.mapper.BookBorrowMapper;
import app.mapper.BookMapper;
import app.model.Book;
import app.repository.RepositoryBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServices {


    private RepositoryBook repositoryBook;

    @Autowired
    public BookServices(RepositoryBook repositoryBook) {
        this.repositoryBook = repositoryBook;
    }


    public List<BookDto> getListBook() {
        return repositoryBook.findAll()
                .stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDto save(BookDto bookDto) {
        Optional<Book> bookbyIsbn = repositoryBook.findByIsbn(bookDto.getIsbn());
        bookbyIsbn.ifPresent(a ->{
            throw new BookAlreadyExistsException();
        });
        return mapAndSave(bookDto);
    }

    private BookDto mapAndSave(BookDto bookDto) {
        Book entity = BookMapper.toEntity(bookDto);
        Book savedBook = repositoryBook.save(entity);
        return BookMapper.toDto(savedBook);
    }

    public BookDto findBookById(Integer id) {
        Optional<Book> bookById = repositoryBook.findById(id);
        Book entity = bookById
                .orElseThrow(BookNoAlreadyExistsException::new);
        return BookMapper.toDto(entity);
    }

    public BookDto update(BookDto bookDto) {
        Optional<Book> entity = repositoryBook.findByIsbn(bookDto.getIsbn());
        entity.ifPresent(e -> {
            if (!e.getId().equals(bookDto.getId())) {
                throw new BookAlreadyExistsException();
            }
        });
        return mapAndSave(bookDto);
    }

    public BookDto delete(Integer id) {
        Optional<Book> magById = repositoryBook.findById(id);
        Book entity = magById.orElseThrow(BookNoAlreadyExistsException::new);
        repositoryBook.delete(entity);
        return BookMapper.toDto(entity);
    }

    public List<BookBorrowDto> findAllUserWhoBorrowBook(Integer idBook) {
        return repositoryBook.findById(idBook)
                .map(Book::getBorrowList)
                .orElseThrow(BookNoAlreadyExistsException::new)
                .stream()
                .map(BookBorrowMapper::toDto)
                .collect(Collectors.toList());
    }
}
