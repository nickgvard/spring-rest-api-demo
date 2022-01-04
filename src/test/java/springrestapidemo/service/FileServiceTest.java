package springrestapidemo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.repository.FileRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Nikita Gvardeev
 * 02.01.2022
 */

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenFindById() {
        FileEntity expected = fileEntity("File1", "Location1");

        given(fileRepository.findById(expected.getId())).willReturn(Optional.of(expected));

        FileEntity actual = fileService.findById(expected.getId());

        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void whenFindAll() {
        List<FileEntity> expected = List
                .of(
                        fileEntity("File1", "Location1"),
                        fileEntity("File2", "Location2"));

        given(fileRepository.findAll()).willReturn(expected);

        List<FileEntity> actual = fileService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void whenSave() {
        FileEntity expected = fileEntity("File1", "Location1");
        FileEntity saved = new FileEntity(null, "File1", "Location1");

        given(fileRepository.save(saved))
                .willReturn(expected);

        FileEntity actual = fileService.save(saved);

        assertEquals(expected, actual);
    }

    @Test
    public void whenUpdate() {
        FileEntity expected = fileEntity("File1", "Location1");
        FileEntity updated = fileEntity("File1", "Location1");

        given(fileRepository.save(any(FileEntity.class))).willReturn(expected);

        FileEntity actual = fileService.update(updated);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDelete() {
        FileEntity deleted = fileEntity("File2", "Location2");
        fileService.delete(deleted);

        verify(fileRepository).delete(deleted);
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