package app.services;

import app.dto.BorrowDto;
import app.exception.BorrowNoExistsException;
import app.exception.InvalidBorrowPublication;
import app.model.Book;
import app.model.Borrow;
import app.model.User;
import app.repository.BorrowRepository;
import app.repository.PublicationRepository;
import app.repository.RepositoryUser;
import org.junit.Test;


import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class BorrowServiceTest {

    @InjectMocks
    BorrowService borrowService;

    @Mock
    BorrowRepository borrowRepository;

    @Mock
    RepositoryUser repositoryUser;

    @Mock
    PublicationRepository publicationRepository;

    private Book book;
    private User user;
    private Borrow borrow;
    private Borrow borrow1;


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

        borrow = new Borrow();
        borrow.setUser(user);
        borrow.setPublication(book);
        borrow.setStart(LocalDate.now());

        borrow1 = new Borrow();
        borrow1.setUser(user);
        borrow1.setPublication(book);
        borrow1.setStart(LocalDate.now());
        borrow1.setEnd(LocalDate.now());

    }

    @Test
    public void shouldThrowExceptionIfUserNoExists() {
        //given
        given(repositoryUser.findById(1)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(InvalidBorrowPublication.class, () -> {
            borrowService.saveBorrow(1, 1);
        });
        then(repositoryUser).should().findById(1);
    }

    @Test
    public void shouldThrowExceptionIfPublicationIsBorrow() {
      //given
        given(borrowRepository.findByPublication_idAndEndIsNull(1))
                .willReturn(Optional.empty());

       //when
       //then
        assertThrows(InvalidBorrowPublication.class, () -> {
            borrowService.saveBorrow(1, 1);
        });
        then(borrowRepository).should().findByPublication_idAndEndIsNull(1);

    }

    @Test
    public void shouldThrowExceptionWhenPublicationNoExists() {

        //given
        Integer integer = 1;
        given(publicationRepository.findById(integer)).willReturn(Optional.empty());
        given(repositoryUser.findById(1)).willReturn(Optional.of(user));

        //when
        //then
        assertThrows(InvalidBorrowPublication.class, () -> {
            borrowService.saveBorrow(integer, integer);}
            );

        then(publicationRepository).should().findById(1);

    }

    @Test
    public void shouldReturnMapAndSaveBorrowBookForUser() {
        //given
        given(repositoryUser.findById(1)).willReturn(Optional.of(user));
        given(publicationRepository.findById(1)).willReturn(Optional.of(book));
        given(borrowRepository.save(borrow)).willReturn(borrow);
        //when
        BorrowDto borrowDto = borrowService.saveBorrow(user.getId(), book.getId());

        //then
        assertThat(borrowDto, notNullValue());
        assertThat(borrowDto.getStart(), is(borrow.getStart()));

        then(borrowRepository).should().save(borrow);

    }

    @Test
    public void shouldThrowExceptionIfBookWhatTryReturnNoExists() {
        //given
        given(borrowRepository.findById(1)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(BorrowNoExistsException.class,() -> borrowService.returnBorrow(1));
        then(borrowRepository).should().findById(1);
    }

    @Test
    public void shouldThrowExceptionIfBookIsReturn() {
        //given
        given(borrowRepository.findById(1)).willReturn(Optional.of(borrow1));
        //when
        //then
        assertThrows(BorrowNoExistsException.class, () -> borrowService.returnBorrow(1));
    }

    @Test
    public void shouldSetEndTime() {
        //given
        given(borrowRepository.findById(1)).willReturn(Optional.of(borrow));
        //when
        LocalDate localDate = borrowService.returnBorrow(1);
        //then
        assertThat(localDate, notNullValue());
        assertThat(borrow.getEnd(), is(localDate));
        assertThat(borrow.getEnd(), is(LocalDate.now()));
    }

    @Test
    public void shouldThrowExceptionWhenBookNoReturn() {
        //given
        given(borrowRepository.findById(1)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(BorrowNoExistsException.class, () -> {
            borrowService.returnBorrow(1);
        });
    }







}