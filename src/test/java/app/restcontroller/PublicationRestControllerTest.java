package app.restcontroller;

import app.dto.BookDto;
import app.dto.MagazineDto;
import app.dto.PublicationDto;
import app.mapper.BookMapper;
import app.mapper.MagazineMapper;
import app.model.Book;
import app.model.Borrow;
import app.model.Magazine;
import app.model.User;
import app.services.BookServices;
import app.services.PublicationServices;
import app.services.UserService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@RunWith(SpringRunner.class)
@WebMvcTest(PublicationRestController.class)
public class PublicationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicationServices publicationServices;




    @Test
    public void shouldReturnAllPublication() throws Exception {
        //given
        given(publicationServices.getAllPublication()).willReturn(getAllPublication());
        //when
        //then
        mockMvc.perform(get("/api/publication"))
                .andExpect(jsonPath("$", hasSize(4)));
    }

    private List<MagazineDto> getAllMagazine() {
        Magazine magazine = new Magazine();
        magazine.setTimePublication(LocalDate.now());
        magazine.setLanguage("Polski");
        magazine.setTitle("ZIEMIA");

        Magazine magazine1 = new Magazine();
        magazine1.setTimePublication(LocalDate.now());
        magazine1.setLanguage("Angielski");
        magazine1.setTitle("Earth");
        MagazineDto magazineDto = MagazineMapper.toDto(magazine);
        MagazineDto magazineDto1 = MagazineMapper.toDto(magazine1);

        return Arrays.asList(magazineDto, magazineDto1);
    }


    private List<BookDto> getAllBook() {
        Book book = new Book();
        book.setAuthor("Dada");
        book.setPages(421);
        book.setIsbn("1521512512");

        Book book1 = new Book();
        book.setAuthor("Dasda");
        book.setPages(4211);
        book.setIsbn("1521512412512");

        BookDto bookDto = BookMapper.toDto(book);
        BookDto bookDto1 = BookMapper.toDto(book1);

        return Arrays.asList(bookDto, bookDto1);
    }

    public List<PublicationDto> getAllPublication() {
        return Arrays.asList(
                getAllBook().get(0),
                getAllBook().get(1),
                getAllMagazine().get(0),
                getAllMagazine().get(1)
        );
    }
}