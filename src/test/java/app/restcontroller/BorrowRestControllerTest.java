package app.restcontroller;

import app.dto.BorrowDto;
import app.services.BorrowService;
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
@WebMvcTest(BorrowRestController.class)
public class BorrowRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowService borrowService;

    private BorrowDto borrowDto;
    private BorrowDto borrowDto1;
    private String api;

    @Before
    public void setUp() {

        api = "/api/borrow";
        borrowDto = new BorrowDto();
        borrowDto.setId(1);
        borrowDto.setPublication_id(1);
        borrowDto.setUser_id(1);
        borrowDto.setStart(LocalDate.now());

        borrowDto1 = new BorrowDto();
        borrowDto1.setPublication_id(1);
        borrowDto1.setUser_id(1);


    }

    @Test
    public void shouldThrowExceptionIfBorrowWhichIsCreatedHaveSetId() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(borrowDto)))
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreatBorrowDto() throws Exception{
        //given
        given(borrowService.saveBorrow(1,1)).willReturn(borrowDto);
        //when
        //then
        mockMvc.perform(post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(borrowDto1)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shouldReturnBookWithSetEndTime() throws Exception {
        //given
        given(borrowService.returnBorrow(1)).willReturn(LocalDate.now());
        //when
        //then
        mockMvc.perform(post(api + "/{id}/end", 1))
                .andExpect(status().isOk());
    }




    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}