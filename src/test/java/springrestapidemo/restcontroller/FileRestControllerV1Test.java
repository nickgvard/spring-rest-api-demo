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
import springrestapidemo.entity.FileEntity;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.service.FileService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Nikita Gvardeev
 * 02.01.2022
 */

@RunWith(SpringRunner.class)
@WebMvcTest(FileRestControllerV1.class)
public class FileRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FileService fileService;

    @Test
    public void findById() throws Exception {
        FileEntity expected = fileEntity("File1", "Location1");

        given(fileService
                .findById(anyLong()))
                .willReturn(expected);

        mockMvc.perform(get("/api/v1/files/{id}", expected.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.location").value(expected.getLocation()));
    }

    @Test
    public void findAll() throws Exception {
        List<FileEntity> expected = List
                .of(fileEntity("File1", "Location1"), fileEntity("File2", "Location2"));

        given(fileService
                .findAll())
                .willReturn(expected);

        mockMvc.perform(get("/api/v1/files")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(expected.get(0).getName())))
                .andExpect(jsonPath("$[0].name", is(expected.get(0).getName())))
                .andExpect(jsonPath("$[1].location", is(expected.get(1).getLocation())))
                .andExpect(jsonPath("$[1].location", is(expected.get(1).getLocation())));
    }

    @Test
    public void save() throws Exception {
        FileEntity expected = fileEntity("File1", "Location1");

        given(fileService
                .save(any()))
                .willReturn(expected);

        mockMvc.perform(post("/api/v1/files")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new FileEntity(
                                null, "File1", null))))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.location", is(expected.getLocation())))
                .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        FileEntity persistFile = fileEntity("File1", "Location1");
        FileEntity expected = fileEntity("File1_1", "Location1_1");

        given(fileService
                .findById(persistFile.getId()))
                .willReturn(persistFile);

        given(fileService
                .update(persistFile))
                .willReturn(expected);

        mockMvc.perform(put("/api/v1/files/{id}", persistFile.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new FileEntity(
                                persistFile.getId(), "File1", null))))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.location", is(expected.getLocation())))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete() throws Exception {
        FileEntity persistFile = fileEntity("File1", "Location1");

        doNothing()
                .when(fileService)
                .delete(persistFile);

        mockMvc.perform(delete("/api/v1/files/{id}", persistFile.getId()))
                .andExpect(status().isAccepted());
    }

    private FileEntity fileEntity(String name, String location) {
        return FileEntity.builder().id(1L).name(name).location(location).build();
    }
}