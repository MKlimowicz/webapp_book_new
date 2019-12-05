package app.services;

import app.dto.BookBorrowDto;
import app.dto.BookDto;
import app.exception.BookAlreadyExistsException;
import app.exception.BookNoAlreadyExistsException;
import app.mapper.BookMapper;
import app.model.Book;
import app.model.Borrow;
import app.model.User;
import app.repository.RepositoryBook;

import org.junit.Test;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class BookServicesTest {

    @InjectMocks
    BookServices bookServices;

    @Mock
    RepositoryBook repositoryBook;

    private BookDto bookDto;
    private BookDto bookDto1;
    private Book book;
    private Book book1;
    private User user;
    private List<Borrow> borrows = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);


        user = new User();
        user.setId(1);
        user.setPesel("412412412");
        user.setLastName("TestoweNazwisko");
        user.setLastName("TestoweImie");



        book = new Book();
        book.setId(1);
        book.setAuthor("Dada");
        book.setPages(421);
        book.setIsbn("1521512512");
        book.setBorrowList(borrows);


        book1 = new Book();
        book1.setId(2);
        book1.setAuthor("Dada");
        book1.setPages(421);
        book1.setIsbn("1521512512");

        Borrow borrow = new Borrow();
        borrow.setId(1);
        borrow.setUser(user);
        borrow.setPublication(book);
        borrow.setStart(LocalDate.now());
        borrow.setEnd(LocalDate.now());

        borrows.add(borrow);

        bookDto1 = BookMapper.toDto(book1);
        bookDto = BookMapper.toDto(book);
    }


    @Test
    public void shouldReturnMapBook() {
        List<Book> books = getAllBook();

        given(repositoryBook.findAll()).willReturn(books);

        List<BookDto> listBookDto = bookServices.getListBook();

        assertThat(listBookDto,hasSize(2));
        assertThat(listBookDto.get(0), instanceOf(BookDto.class));
        then(repositoryBook).should().findAll();
    }

    @Test
    public void shouldSaveBookAndMapToBookDto() {
        given(repositoryBook.save(book)).willReturn(book);

        BookDto save = bookServices.save(bookDto);

        assertThat(save, instanceOf(BookDto.class));
        assertThat(save.getAuthor(), equalTo(book.getAuthor()));
        assertThat(save.getIsbn(), equalTo(book.getIsbn()));
        then(repositoryBook).should().findByIsbn(bookDto.getIsbn());
    }



    @Test
    public void shouldThrowExceptionIfExistsBookWithThisSameIsbn() {
        //given
        given(repositoryBook.findByIsbn(bookDto.getIsbn())).willReturn(Optional.of(book));
        //when
        //then
        assertThrows(BookAlreadyExistsException.class, () -> {
            bookServices.save(bookDto);
        });
    }

    @Test
    public void shouldReturnMapBookDtoWithThisSameId() {
        //given
        given(repositoryBook.findById(1)).willReturn(Optional.of(book));
        //when
        BookDto bookById = bookServices.findBookById(1);
        //then
        then(repositoryBook).should().findById(1);
        assertThat(bookById.getId(), is(book.getId()));
        then(repositoryBook).should().findById(1);

    }

    @Test
    public void shouldThrowExceptionIfBookNoExistsById() {
        //given
        given(repositoryBook.findById(1)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(BookNoAlreadyExistsException.class , () -> bookServices.findBookById(1));

    }

    @Test
    public void shouldThrowExceptionIfBookWithThisSameIsbnExists() {
        //given
        given(repositoryBook.findByIsbn(bookDto.getIsbn()))
                .willReturn(Optional.of(book));
        //when
        //then
        assertThrows(BookAlreadyExistsException.class, () -> {
            bookServices.update(bookDto1);
        });
    }

    @Test
    public void shouldReturnUpdatedBookDto() {
        //given
        given(repositoryBook.save(book)).willReturn(book);
        //when
        BookDto update = bookServices.update(bookDto);
        //then
        assertThat(update.getIsbn(), equalTo(bookDto.getIsbn()));
        assertThat(update.getTitle(), equalTo(bookDto.getTitle()));
        then(repositoryBook).should().findByIsbn(bookDto.getIsbn());
    }

    @Test
    public void shouldThrowExceptionIfBookWithIdNoExists() {
        //given
        given(repositoryBook.findById(1)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(BookNoAlreadyExistsException.class, () -> {
           bookServices.delete(1);
        });
    }

    @Test
    public void shouldDeleteBook() {
        //given
        given(repositoryBook.findById(1)).willReturn(Optional.of(book));
        //when
        BookDto deletedBook = bookServices.delete(1);
        //then
        assertNotNull(deletedBook);
        assertThat(deletedBook.getId(), is(1));
        then(repositoryBook).should().delete(book);
    }

    @Test
    public void shouldReturnAllUserWhoBorrowBookById() {
        //given
        given(repositoryBook.findById(1)).willReturn(Optional.of(book));
        //when
        List<BookBorrowDto> allUserWhoBorrowBook = bookServices.findAllUserWhoBorrowBook(1);
        //then
        assertThat(allUserWhoBorrowBook, hasSize(1));
        assertThat(allUserWhoBorrowBook.get(0), instanceOf(BookBorrowDto.class));
        assertThat(allUserWhoBorrowBook.get(0).getId(), is(1));
        assertThat(allUserWhoBorrowBook.get(0).getPesel(), equalTo(user.getPesel()));
    }



    List<Book> getAllBook() {
        Book book = new Book();
        book.setAuthor("Dada");
        book.setPages(421);
        book.setIsbn("1521512512");

        Book book1 = new Book();
        book.setAuthor("Dasda");
        book.setPages(4211);
        book.setIsbn("1521512412512");

        return Arrays.asList(book, book1);
    }


}