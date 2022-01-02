package springrestapidemo.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.restcontroller.UserRestControllerV1;
import springrestapidemo.service.UserService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Nikita Gvardeev
 * 01.01.2022
 */

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestControllerV1.class)
public class UserRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void findById() throws Exception {
        UserEntity expected = userEntity("Bob");

        given(userService
                .findById(anyLong()))
                .willReturn(expected);

        mockMvc.perform(get("/api/v1/users/{id}", expected.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.name").value(expected.getName()));
    }

    @Test
    public void findAll() throws Exception {
        List<UserEntity> expected = List
                .of(userEntity("Nick"), userEntity("Bob"));

        given(userService
                .findAll())
                .willReturn(expected);

        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(expected.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(expected.get(1).getName())));
    }

    @Test
    public void save() throws Exception {
        UserEntity expected = userEntity("Nick");

        given(userService
                .save(any()))
                .willReturn(expected);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new UserEntity(
                                null, "Nick", null))))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        UserEntity persistUser = userEntity("Bob");
        UserEntity expected = userEntity("Nick");

        given(userService
                .findById(persistUser.getId()))
                .willReturn(persistUser);

        given(userService
                .update(persistUser))
                .willReturn(expected);

        mockMvc.perform(put("/api/v1/users/{id}", persistUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new UserEntity(
                                persistUser.getId(), "Nick", null))))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete() throws Exception {
        UserEntity persistUser = userEntity("Nick");

        doNothing().when(userService).delete(persistUser);

        mockMvc.perform(delete("/api/v1/users/{id}", persistUser.getId()))
                .andExpect(status().isAccepted());
    }

    private UserEntity userEntity(String name) {
        return UserEntity.builder().id(1L).name(name).build();
    }
}