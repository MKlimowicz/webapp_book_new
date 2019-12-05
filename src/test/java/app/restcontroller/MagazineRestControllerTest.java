package app.restcontroller;

import app.dto.MagazineDto;
import app.mapper.MagazineMapper;
import app.model.Magazine;
import app.services.MagazineServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(MagazineRestController.class)
public class MagazineRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MagazineServices magazineServices;

    private String api = "/api/magazine";

    @Before
    public void setUp() {

    }

    @Test
    public void shouldReturnAllMagazine() throws Exception {
        //given
        given(magazineServices.getListMagazine()).willReturn(getAllMagazine());
        //when
        //then
        mockMvc.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void shouldReturnSavedDtoWithSetId() throws Exception {
        //given
        MagazineDto magazineDtoSave = getAllMagazine().get(0);
        MagazineDto magazineDtoReturn = getAllMagazine().get(1);
        given(magazineServices.save(magazineDtoSave)).willReturn(magazineDtoReturn);
        //when
        //then
        mockMvc.perform(post(api)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(magazineDtoSave)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.language", equalTo("Angielski")));

    }

    @Test
    public void shouldThrowExceptionWhenTrySaveDtoWithSetId() throws Exception{
        //given
        MagazineDto magazineDtoSave = getAllMagazine().get(1);
        //when
        //then
        mockMvc.perform(post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(magazineDtoSave)))
                    .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldThrowExceptionWhenTryUpdatedDtoWithDifferentIdThenIdWithPath() throws Exception {
        //given
        MagazineDto magazineDto = getAllMagazine().get(1);
        //when
        //then
        mockMvc.perform(put(api + "/{id}", 2 )
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(magazineDto)))
                    .andExpect(status().isConflict());
    }

    @Test
    public void shouldReturnUpdatedDto() throws Exception{
        //given
        MagazineDto magazineDto = getAllMagazine().get(1);
        given(magazineServices.update(magazineDto)).willReturn(magazineDto);
        //when
        //then
        mockMvc.perform(put(api + "/{id}", 1 )
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(magazineDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.language",  equalTo("Angielski")));
    }


    @Test
    public void shouldThrowExceptionWhenIdMagazineToDeleteIsNull() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete(api + "/{id}", nullValue()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnDeletedMagazine() throws Exception {
        //given
        MagazineDto magazineDto = getAllMagazine().get(1);
        given(magazineServices.delete(1)).willReturn(magazineDto);
        //when
        //then
        mockMvc.perform(delete(api + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }





    List<MagazineDto> getAllMagazine() {
        Magazine magazine = new Magazine();
        magazine.setTimePublication(LocalDate.now());
        magazine.setLanguage("Polski");
        magazine.setTitle("ZIEMIA");

        Magazine magazine1 = new Magazine();
        magazine1.setId(1);
        magazine1.setTimePublication(LocalDate.now());
        magazine1.setLanguage("Angielski");
        magazine1.setTitle("Earth");

        return Arrays.asList(MagazineMapper.toDto(magazine), MagazineMapper.toDto(magazine1));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}