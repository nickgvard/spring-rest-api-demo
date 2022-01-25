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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springrestapidemo.dto.FileDto;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.service.FileService;
import springrestapidemo.service.AmazonS3FileService;

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
@WebMvcTest(value = FileRestControllerV1.class,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = FileRestControllerV1.class)})
@AutoConfigureMockMvc(addFilters = false)
public class FileRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FileService fileService;

    @MockBean
    private AmazonS3FileService s3FileService;

    @MockBean
    private MockMultipartFile mockMultipartFile;

    @Test
    public void whenFindById() throws Exception {
        FileDto expected = fileDto("File1", "Location1");

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
    public void whenFindAll() throws Exception {
        List<FileDto> expected = List
                .of(fileDto("File1", "Location1"), fileDto("File2", "Location2"));

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
    public void whenSave() throws Exception {
        FileDto expected = fileDto("File1", "Location1");

        given(fileService
                .save(any()))
                .willReturn(expected);

        given(s3FileService
                .uploadFileToAmazon(any(), any()))
                .willReturn(expected);

        mockMvc.perform(multipart("/api/v1/files")
                .file("file", mockMultipartFile.getBytes())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.location", is(expected.getLocation())))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdate() throws Exception {
        FileDto persistFile = fileDto("File1", "Location1");
        FileDto expected = fileDto("File1_1", "Location1_1");

        given(fileService
                .findById(anyLong()))
                .willReturn(persistFile);

        given(fileService
                .update(any(), anyLong()))
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

        doNothing()
                .when(fileService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/v1/files/{id}", anyLong()))
                .andExpect(status().isAccepted());
    }

    private FileDto fileDto(String name, String location) {
        return FileDto
                .builder()
                .id(1L)
                .name(name)
                .location(location)
                .build();
    }
}