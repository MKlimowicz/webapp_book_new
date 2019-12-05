package app.services;

import app.dto.UserBorrowDto;
import app.dto.UserDto;
import app.exception.UserAlreadyExistsException;
import app.exception.UserNoAlreadyExistsException;
import app.mapper.UserMapper;
import app.model.Book;
import app.model.Borrow;
import app.model.User;
import app.repository.RepositoryUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    RepositoryUser repositoryUser;

    private User user;
    private User user1;
    private UserDto userDto;
    private UserDto userDto1;
    private Book book;
    private Borrow borrow;
    private Borrow borrow1;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        user1 = new User();
        user1.setId(2);
        user1.setFirstName("MARCIN");
        user1.setLastName("KLIMOWICZ");
        user1.setPesel("421421442141");
        userDto1 = UserMapper.toDto(user1);

        book = new Book();
        book.setId(1);
        book.setAuthor("Dada");
        book.setPages(421);
        book.setIsbn("1521512512");

        borrow = new Borrow();
        borrow.setId(1);
        borrow.setUser(user1);
        borrow.setPublication(book);
        borrow.setStart(LocalDate.now());

        borrow1 = new Borrow();
        borrow.setId(2);
        borrow1.setUser(user1);
        borrow1.setPublication(book);
        borrow1.setStart(LocalDate.now());
        borrow1.setEnd(LocalDate.now());

        user = new User();
        user.setId(1);
        user.setFirstName("MARCIN");
        user.setLastName("KLIMOWICZ");
        user.setPesel("421421442141");
        user.setBorrowBooks(Arrays.asList(borrow, borrow1));
        userDto = UserMapper.toDto(user);




    }


    @Test
    public void shouldReturnUsers() {
        //give
        given(repositoryUser.findAll()).willReturn(getUsers());
        //when
        List<UserDto> users = userService.getUsers();
        //then
        assertThat(users, hasSize(2));
        assertThat(users.get(0).getFirstName(), equalTo("MARCIN"));

    }

    @Test
    public void shouldThrowExceptionIfExistsPersonWithPeselWithWhomTrySave() {
        //given
        given(repositoryUser.findByPesel("421421442141")).willReturn(Optional.of(user));
        //when
        //then
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.save(userDto);
        });
    }

    @Test
    public void shouldReturnMapAndSaveUser() {
        //given
        given(repositoryUser.findByPesel("421421442141")).willReturn(Optional.empty());
        given(repositoryUser.save(user)).willReturn(user);
        //when
        UserDto save = userService.save(userDto);
        //then
        assertThat(save, notNullValue());
    }

    @Test
    public void shouldThrowExceptionIfUserNoExists() {
        //given
        given(repositoryUser.findById(1)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(UserNoAlreadyExistsException.class, () -> {
            userService.findById(1);
        });

    }

    @Test
    public void shouldReturnUserById() {
        //given
        given(repositoryUser.findById(1)).willReturn(Optional.of(user));
        //when
        UserDto byId = userService.findById(1);
        //then
        assertThat(byId, notNullValue());
        assertThat(byId.getFirstName(), equalTo(user.getFirstName()));
    }

    @Test
    public void shouldThrowExceptionIfExistsUserWithThisSamePeselHowUpdatedUser() {
        //given
        given(repositoryUser.findByPesel("421421442141")).willReturn(Optional.of(user));
        //when
        //then
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.update(userDto1);
        });
    }

    @Test
    public void shouldReturnMapAndUpdateUser() {
        //given
        given(repositoryUser.findByPesel("421421442141")).willReturn(Optional.empty());
        given(repositoryUser.save(user)).willReturn(user);
        //when
        UserDto updated = userService.update(userDto);
        //then
        assertThat(updated, notNullValue());
    }

    @Test
    public void shouldDeleteUserById() {
        //given
        given(repositoryUser.findById(1)).willReturn(Optional.of(user));
        //when
        UserDto deleted = userService.delete(1);
        //then
        assertThat(deleted, notNullValue());
        assertThat(deleted.getId(), equalTo(user.getId()));
        then(repositoryUser).should().deleteById(1);
    }

    @Test
    public void shouldThrowExceptionIfRemovableUserNoExists() {
        //given
        given(repositoryUser.findById(1)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(UserNoAlreadyExistsException.class, () -> {
            userService.delete(1);
        });

    }

    @Test
    public void shouldReturnBorrowHistoryUser() {
        //given
        given(repositoryUser.findById(1)).willReturn(Optional.of(user));
        //when
        List<UserBorrowDto> borrowPublication = userService.findBorrowPublication(1);
        //then
        assertThat(borrowPublication, hasSize(2));
    }

    @Test
    public void shouldThrowExceptionWhenUserNoExists() {
        //gvien
        given(repositoryUser.findById(1)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(UserNoAlreadyExistsException.class, () -> {
            userService.findBorrowPublication(1);
        });
    }




    List<User> getUsers() {
        User user = new User();
        user.setFirstName("MARCIN");
        user.setLastName("KLIMOWICZ");
        user.setPesel("421421442141");

        User user1 = new User();
        user1.setFirstName("ANDRZEJ");
        user1.setLastName("WOJCIECH");
        user1.setPesel("421421442141");

        return Arrays.asList(user, user1);

    }
}