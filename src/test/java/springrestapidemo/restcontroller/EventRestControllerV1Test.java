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
import springrestapidemo.dto.EventDto;
import springrestapidemo.dto.FileDto;
import springrestapidemo.dto.UserDto;
import springrestapidemo.entity.EventEntity;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.service.EventService;

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
 * 02.01.2022
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = EventRestControllerV1.class,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = EventRestControllerV1.class)
        })
@AutoConfigureMockMvc(addFilters = false)
public class EventRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;


    @Test
    public void whenFindById() throws Exception {
        EventDto expected = eventDto("Description1");

        given(eventService
                .findById(anyLong()))
                .willReturn(expected);

        mockMvc.perform(get("/api/v1/events/{id}", expected.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.userDto").exists())
                .andExpect(jsonPath("$.fileDto").exists())
                .andExpect(jsonPath("$.description", is(expected.getDescription())));
    }

    @Test
    public void whenFindAll() throws Exception {
        List<EventDto> expected = List
                .of(eventDto("Description1"), eventDto("Description2"));

        given(eventService
                .findAll())
                .willReturn(expected);

        mockMvc.perform(get("/api/v1/events")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userDto").exists())
                .andExpect(jsonPath("$[0].fileDto").exists())
                .andExpect(jsonPath("$[0].description", is(expected.get(0).getDescription())))
                .andExpect(jsonPath("$[1].userDto").exists())
                .andExpect(jsonPath("$[1].fileDto").exists())
                .andExpect(jsonPath("$[1].description", is(expected.get(1).getDescription())));
    }

    @Test
    public void whenSave() throws Exception {
        EventDto expected = eventDto("Description1");

        given(eventService
                .save(any()))
                .willReturn(expected);

        mockMvc.perform(post("/api/v1/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new EventEntity(
                                null, UserEntity.builder().build(), FileEntity.builder().build(), "Description1"))))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userDto").exists())
                .andExpect(jsonPath("$.fileDto").exists())
                .andExpect(jsonPath("$.description", is(expected.getDescription())))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdate() throws Exception {
        EventDto persistFile = eventDto("Description1");
        EventDto expected = eventDto("Description1_1");

        given(eventService
                .findById(anyLong()))
                .willReturn(persistFile);

        given(eventService
                .update(any(), anyLong()))
                .willReturn(expected);

        mockMvc.perform(put("/api/v1/events/{id}", persistFile.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new EventEntity(
                                persistFile.getId(), UserEntity.builder().build(), FileEntity.builder().build(), "Description1"))))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userDto").exists())
                .andExpect(jsonPath("$.fileDto").exists())
                .andExpect(jsonPath("$.description", is(expected.getDescription())))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete() throws Exception {

        doNothing()
                .when(eventService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/v1/events/{id}", anyLong()))
                .andExpect(status().isAccepted());
    }

    private EventDto eventDto(String desc) {
        return EventDto
                .builder()
                .id(1L)
                .userDto(UserDto.builder().build())
                .fileDto(FileDto.builder().build())
                .description(desc)
                .build();
    }
}