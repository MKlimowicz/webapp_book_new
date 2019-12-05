package app.services;

import app.dto.BorrowDto;
import app.dto.MagazineDto;
import app.exception.BorrowNoExistsException;
import app.exception.InvalidBorrowPublication;
import app.exception.MagazineAlreadyExistsException;
import app.exception.MagazineNoExistsException;
import app.mapper.MagazineMapper;
import app.model.*;
import app.repository.BorrowRepository;
import app.repository.PublicationRepository;
import app.repository.RepositoryMagazine;
import app.repository.RepositoryUser;
import org.junit.Test;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class MagazineServicesTest {

    @InjectMocks
    MagazineServices magazineServices;

    @Mock
    RepositoryMagazine repositoryMagazine;

    private Magazine magazine;
    private MagazineDto magazineDto;
    private Magazine magazine1;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        magazine = new Magazine();
        magazine.setId(1);
        magazine.setTitle("POLSKA");
        magazine.setLanguage("Polski");
        magazine.setTimePublication(LocalDate.now());
        magazineDto = MagazineMapper.toDto(magazine);

        magazine1 = new Magazine();
        magazine1.setId(2);
        magazine1.setTitle("POLSKA");
        magazine1.setLanguage("Polski");
        magazine1.setTimePublication(LocalDate.now());
    }

    @Test
    public void shouldReturnListWithMagazineDto() {
        //given
        given(repositoryMagazine.findAll()).willReturn(getAllMagazine());
        //when
        List<MagazineDto> listMagazine = magazineServices.getListMagazine();
        //then
        assertThat(listMagazine, hasSize(2));
        assertThat(listMagazine.get(0), instanceOf(MagazineDto.class));
        assertThat(listMagazine.get(1).getLanguage(), is("Angielski"));
        then(repositoryMagazine).should().findAll();
    }

    @Test
    public void shouldThrowExceptionIfMagazineWithThisSameTitleExists() {
        //given
        given(repositoryMagazine.findByTitle("POLSKA")).willReturn(Optional.of(magazine));
        //when
        //then
        assertThrows(MagazineAlreadyExistsException.class, () -> {
            magazineServices.save(magazineDto);
        });

        then(repositoryMagazine).should().findByTitle(magazineDto.getTitle());
    }

    @Test
    public void shouldMapAndSaveMagazineDto() {
        //given
        given(repositoryMagazine.findByTitle("POLSKA")).willReturn(Optional.empty());
        given(repositoryMagazine.save(magazine)).willReturn(magazine);
        //when
        MagazineDto savedMagazine = magazineServices.save(magazineDto);
        //then
        assertThat(savedMagazine, notNullValue());
        assertThat(savedMagazine.getTitle(), equalTo(magazine.getTitle()));
        assertThat(savedMagazine.getTitle(), equalTo("POLSKA"));

        then(repositoryMagazine).should().findByTitle("POLSKA");
        then(repositoryMagazine).should().save(magazine);
    }

    @Test
    public void shouldReturnMagazineById() {
        //given
        given(repositoryMagazine.findById(1)).willReturn(Optional.of(magazine));
        //when
        MagazineDto magazineById = magazineServices.findMagazineById(1);
        //then
        assertThat(magazineById.getTitle(),  equalTo(magazine.getTitle()));
        then(repositoryMagazine).should().findById(1);
    }

    @Test
    public void shouldThrowExceptionIfMagazineNoExists() {
        //given
        given(repositoryMagazine.findById(1)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(MagazineNoExistsException.class, () -> {
            magazineServices.findMagazineById(1);
        });
    }

    @Test
    public void shouldReturnUpdateMagazine() {
        //given
        given(repositoryMagazine.findByTitle("POLSKA")).willReturn(Optional.of(magazine));
        given(repositoryMagazine.save(magazine)).willReturn(magazine);
        //when
        MagazineDto update = magazineServices.update(magazineDto);
        //then
        assertThat(update, notNullValue());
        assertThat(update.getId(), is(magazine.getId()));

        then(repositoryMagazine).should().findByTitle(magazineDto.getTitle());
        then(repositoryMagazine).should().save(magazine);

    }

    @Test
    public void shouldThrowExceptionIfMagazineWithThisSameTitleHaveDifferentId() {
        //given
        given(repositoryMagazine.findByTitle("POLSKA")).willReturn(Optional.of(magazine1));
        //when
        //then
        assertThrows(MagazineAlreadyExistsException.class, () -> {
           magazineServices.update(magazineDto);
        });
    }

    @Test
    public void shouldReturnDeleteMagazineById() {
        //given
        given(repositoryMagazine.findById(1)).willReturn(Optional.of(magazine));
        //when
        MagazineDto deleted = magazineServices.delete(1);
        //then
        assertThat(magazineDto, notNullValue());
        assertThat(deleted.getId(), is(1));
        assertThat(deleted.getTitle(),equalTo(magazine.getTitle()));
        then(repositoryMagazine).should().delete(magazine);
    }

    @Test
    public void shouldThrowExceptionWhenWeTryDeleteMagazineWhichNoExists() {
        //given
        given(repositoryMagazine.findById(1)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(MagazineNoExistsException.class, () -> {
            magazineServices.delete(1);
        });
    }


    List<Magazine> getAllMagazine() {
        Magazine magazine = new Magazine();
        magazine.setTimePublication(LocalDate.now());
        magazine.setLanguage("Polski");
        magazine.setTitle("ZIEMIA");

        Magazine magazine1 = new Magazine();
        magazine1.setTimePublication(LocalDate.now());
        magazine1.setLanguage("Angielski");
        magazine1.setTitle("Earth");

        return Arrays.asList(magazine, magazine1);
    }

}