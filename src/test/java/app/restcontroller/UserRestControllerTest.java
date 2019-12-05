package app.restcontroller;



import app.dto.UserDto;

import app.mapper.UserMapper;


import app.model.User;
import app.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;




import java.util.Arrays;
import java.util.List;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDto userDto;
    private UserDto userDto1;
    private final String api = "/api/user";

    @Before
    public void setUp() {
        userDto = getUsersDto().get(0);
        userDto1 = getUsersDto().get(1);

    }


    @Test
    public void shouldReturnUsers() throws Exception {
        //given
        given(userService.getUsers()).willReturn(getUsersDto());
        //when
        //then
        mockMvc.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", equalTo("MARCIN")));

    }

    @Test
    public void shouldThrowExceptionIfIdIsSet() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(post(api)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCreatedBookWithSetId() throws Exception {
        //given
        given(userService.save(userDto1)).willReturn(userDto);
        //when
        //then
        mockMvc.perform(post(api)
                    .contentType(APPLICATION_JSON)
                    .content(asJsonString(userDto1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", equalTo("MARCIN")));
    }

    @Test
    public void shouldThrowExceptionIfIdInPathIsDifferentThanIdWithUserDto() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(put(api + "/{id}", 2)
                    .contentType(APPLICATION_JSON)
                    .content(asJsonString(userDto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldReturnUpdatedBook() throws Exception{
        //given
        given(userService.update(userDto)).willReturn(userDto);
        //when
        //then
        mockMvc.perform(put(api + "/{id}", 1)
                    .contentType(APPLICATION_JSON)
                    .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shouldThrowExceptionIfIdBookToDeleteIsNull() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete(api + "/{id}", nullValue()))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturnDeletedBook() throws Exception {
        //given
        given(userService.delete(1)).willReturn(userDto);
        //when
        //then
        mockMvc.perform(delete(api + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }



    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    List<UserDto> getUsersDto() {
        User user = new User();
        user.setId(1);
        user.setFirstName("MARCIN");
        user.setLastName("KLIMOWICZ");
        user.setPesel("421421442141");
        UserDto dto = UserMapper.toDto(user);

        User user1 = new User();
        user1.setFirstName("ANDRZEJ");
        user1.setLastName("WOJCIECH");
        user1.setPesel("421421442141");
        UserDto dto1 = UserMapper.toDto(user1);

        return Arrays.asList(dto, dto1);

    }

}