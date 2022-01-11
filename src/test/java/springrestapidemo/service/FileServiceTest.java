package springrestapidemo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import springrestapidemo.dto.FileDto;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.repository.FileRepository;
import springrestapidemo.service.amazon.AmazonS3FileService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Nikita Gvardeev
 * 02.01.2022
 */

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private AmazonS3FileService s3FileService;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private FileService fileService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenFindById() {
        FileDto expected = fileDto("File1", "Location1");

        given(fileRepository.findById(expected.getId())).willReturn(Optional.of(FileDto.toEntity(expected)));

        FileDto actual = fileService.findById(expected.getId());

        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void whenFindAll() {
        List<FileDto> expected = List
                .of(
                        fileDto("File1", "Location1"),
                        fileDto("File2", "Location2"));

        given(fileRepository.findAll()).willReturn(expected
                .stream()
                .map(FileDto::toEntity).collect(Collectors.toList()));

        List<FileDto> actual = fileService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void whenSave() {
        FileEntity expected = fileEntity("File1", "Location1");

        FileDto saved = FileDto
                .builder()
                .id(null)
                .name("File1")
                .location("Location1")
                .build();

        given(s3FileService.uploadFileToAmazon(any(), any())).willReturn(saved);

        given(fileRepository.save(any())).willReturn(expected);

        FileDto actual = fileService.save(multipartFile);

        assertEquals(FileDto.toDto(expected), actual);
    }

    @Test
    public void whenUpdate() {
        FileEntity expected = fileEntity("File1", "Location1");

        FileDto updated = fileDto("File1", "Location1");

        given(fileRepository.findById(anyLong())).willReturn(Optional.of(expected));

        given(fileRepository.save(any())).willReturn(expected);

        FileDto actual = fileService.update(updated, updated.getId());

        assertEquals(FileDto.toDto(expected), actual);
    }

    @Test
    public void whenDelete() {
        FileEntity deleted = FileEntity.builder().id(1L).build();

        when(fileRepository.findById(anyLong())).thenReturn(Optional.of(deleted));

        fileService.delete(deleted.getId());

        verify(fileRepository).delete(any());
    }

    private FileDto fileDto(String name, String location) {
        return FileDto
                .builder()
                .id(1L)
                .name(name)
                .location(location)
                .build();
    }

    private FileEntity fileEntity(String name, String location) {
        return FileEntity
                .builder()
                .id(1L)
                .name(name)
                .location(location)
                .build();
    }
}