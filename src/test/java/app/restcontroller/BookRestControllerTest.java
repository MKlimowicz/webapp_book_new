package app.restcontroller;

import app.dto.BookBorrowDto;
import app.dto.BookDto;
import app.mapper.BookMapper;
import app.model.Book;

import app.services.BookServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@RunWith(SpringRunner.class)
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServices bookServices;




    private Book book;
    private Book book1;
    private BookDto bookDto;
    private BookDto bookDto1;
    private String api;

    @Before
    public void setUp() {

        api = "/api/book";

        book = new Book();
        book.setId(1);
        book.setAuthor("Dada");
        book.setPages(421);
        book.setIsbn("1521512512");
        book.setPublisher("MARCIN");
        book.setTitle("KSIAZKA");
        book.setYear(2019);
        bookDto = BookMapper.toDto(book);

        book1 = new Book();
        book1.setAuthor("Dada");
        book1.setPages(421);
        book1.setIsbn("1521512512");
        book1.setPublisher("MARCIN");
        book1.setTitle("KSIAZKA");
        book1.setYear(2019);
        bookDto1 = BookMapper.toDto(book1);


    }

    @Test
    public void shouldReturnBooksList() throws Exception {
        //given
        given(bookServices.getListBook()).willReturn(getAllBook());
        //when
        //then
        mockMvc.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldReturnBadRequestIfBookDtoHaveSetId() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                .post(api)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(bookDto)))
                .andExpect(status().isBadRequest());

    }


    @Test
    public void shouldReturnSavedBook() throws Exception {
        //given
        given(bookServices.save(bookDto1)).willReturn(bookDto);
        //when
        //then
        this.mockMvc.perform(post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookDto1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.author", equalTo("Dada")));



    }

    @Test
    public void getUserWhoBorrowBookById() throws Exception {
        //given
        given(bookServices.findAllUserWhoBorrowBook(1)).willReturn(getBookBorrowDto());
        //when
        //then
        mockMvc.perform(get(api + "/{id}/borrowBook", 1)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0]", notNullValue()));



    }

    @Test
    public void shouldReturnUpdatedBook() throws Exception {
        //given
        given(bookServices.save(bookDto)).willReturn(bookDto);
        //when
        //then
        mockMvc.perform(put(api + "/{id}", 1)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", equalTo("BOOK")));



    }

    @Test
    public void shouldThrowExceptionWhenWeTryUpdatedBookWithDifferentIdThenInPath() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(put(api + "/{id}", 2))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void shouldThrowExceptionIfIdInPathIsNull() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(delete(api + "/{id}",nullValue()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnDeletedBook() throws Exception {
        //given
        given(bookServices.delete(1)).willReturn(bookDto);
        //when
        //then
        mockMvc.perform(delete(api + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",  is(1)))
                .andExpect(jsonPath("$.author", equalTo("Dada")));

        then(bookServices).should().delete(1);

    }



    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    List<BookDto> getAllBook() {
        Book book = new Book();
        book.setAuthor("Dada");
        book.setPages(421);
        book.setIsbn("1521512512");

        Book book1 = new Book();
        book.setAuthor("Dasda");
        book.setPages(4211);
        book.setIsbn("1521512412512");

        return Arrays.asList(BookMapper.toDto(book), BookMapper.toDto(book1));
    }

    List<BookBorrowDto> getBookBorrowDto() {
        BookBorrowDto bookBorrowDto = new BookBorrowDto();
        bookBorrowDto.setPesel("412412412");
        bookBorrowDto.setFirstName("DA");
        bookBorrowDto.setLastName("DO");
        bookBorrowDto.setIdUser(1);
        bookBorrowDto.setId(1);
        bookBorrowDto.setEnd(LocalDate.now());
        bookBorrowDto.setEnd(LocalDate.now());

        BookBorrowDto bookBorrowDto1 = new BookBorrowDto();
        bookBorrowDto1.setPesel("4124124121");
        bookBorrowDto1.setFirstName("Di");
        bookBorrowDto1.setLastName("Du");
        bookBorrowDto1.setIdUser(2);
        bookBorrowDto1.setId(2);
        bookBorrowDto1.setEnd(LocalDate.now());
        bookBorrowDto1.setEnd(LocalDate.now());

        return Arrays.asList(bookBorrowDto, bookBorrowDto1);
    }

}