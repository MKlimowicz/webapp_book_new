package app.services;

import java.time.LocalDate;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;


import app.dto.BookDto;
import app.dto.MagazineDto;
import app.dto.PublicationDto;
import app.mapper.BookMapper;
import app.mapper.MagazineMapper;
import app.model.Book;
import app.model.Magazine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class PublicationServicesTest {

    @InjectMocks
    PublicationServices publicationServices;

    @Mock
    BookServices bookServices;

    @Mock
    MagazineServices magazineServices;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnPublications() {
        //given
        given(bookServices.getListBook()).willReturn(getAllBook());
        given(magazineServices.getListMagazine()).willReturn(getAllMagazine());
        //when
        List<PublicationDto> allPublication = publicationServices.getAllPublication();
        //then
        assertThat(allPublication.get(0), instanceOf(BookDto.class));
        assertThat(allPublication.get(2), instanceOf(MagazineDto.class));
        assertThat(allPublication, hasSize(4));
    }

    @Test
    public void shouldReturnAllMagazine() {
        //given
        given(magazineServices.getListMagazine()).willReturn(getAllMagazine());
        //when
        List<PublicationDto> allMagazine = publicationServices.getAllMagazine();
        //then
        assertThat(allMagazine, hasSize(2));
        assertThat(allMagazine.get(1), instanceOf(MagazineDto.class));

    }

    @Test
    public void shouldReturnAllBook() {
        //given
        given(bookServices.getListBook()).willReturn(getAllBook());
        //when
        List<PublicationDto> allBook = publicationServices.getAllBook();
        //then
        assertThat(allBook, hasSize(2));
        assertThat(allBook.get(1), instanceOf(BookDto.class));

    }


    List<MagazineDto> getAllMagazine() {
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


    List<BookDto> getAllBook() {
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

}