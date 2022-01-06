package springrestapidemo.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.service.UserService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Nikita Gvardeev
 * 01.01.2022
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserRestControllerV1.class,
        useDefaultFilters = false,
        includeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = UserRestControllerV1.class)})
@AutoConfigureMockMvc(addFilters = false)
public class UserRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void whenFindById() throws Exception {
        UserEntity expected = userEntity("bob@email.com");

        given(userService
                .findById(anyLong()))
                .willReturn(expected);

        mockMvc.perform(get("/api/v1/users/{id}", expected.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.email").value(expected.getEmail()))
                .andExpect(status().isOk());
    }

    @Test
    public void whenFindAll() throws Exception {
        List<UserEntity> expected = List
                .of(userEntity("nick@email.com"), userEntity("bob@email.com"));

        given(userService
                .findAll())
                .willReturn(expected);

        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is(expected.get(0).getEmail())))
                .andExpect(jsonPath("$[1].email", is(expected.get(1).getEmail())));
    }

    @Test
    public void whenSave() throws Exception {
        UserEntity expected = userEntity("nick@email.com");

        given(userService
                .save(any()))
                .willReturn(expected);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        UserEntity
                                .builder()
                                .id(null)
                                .email("nick@email.com")
                                .eventEntities(null)
                                .build())))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenUpdate() throws Exception {
        UserEntity persistUser = userEntity("bob@email.com");
        UserEntity expected = userEntity("nick@email.com");

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
                        UserEntity
                                .builder()
                                .id(persistUser.getId())
                                .email("nick@email.com")
                                .eventEntities(null)
                                .build())))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete() throws Exception {
        UserEntity persistUser = userEntity("nick@email.com");

        doNothing().when(userService).delete(persistUser);

        mockMvc.perform(delete("/api/v1/users/{id}", persistUser.getId()))
                .andExpect(status().isAccepted());
    }

    private UserEntity userEntity(String email) {
        return UserEntity.builder().id(1L).email(email).build();
    }
}