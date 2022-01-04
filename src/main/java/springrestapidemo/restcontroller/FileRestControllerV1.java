package springrestapidemo.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springrestapidemo.dto.FileDto;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.service.FileService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@RestController
@RequestMapping("/api/v1/files")
public class FileRestControllerV1 {

    private FileService fileService;

    public FileRestControllerV1(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public List<FileDto> findAll() {
        return fileService.findAll()
                .stream()
                .map(FileDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public FileDto findById(@PathVariable Long id) {
        return FileDto.toDto(fileService.findById(id));
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public FileDto save(@RequestBody FileDto fileDto) {
        FileEntity file = fileService.save(FileDto.toEntity(fileDto));
        return FileDto.toDto(file);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @ResponseStatus(HttpStatus.OK)
    public FileDto update(@RequestBody FileDto fileDto, @PathVariable Long id) {
        FileEntity file = fileService.findById(id);
        file.setName(fileDto.getName());
        file.setLocation(fileDto.getLocation());
        return FileDto.toDto(fileService.update(file));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        FileEntity file = fileService.findById(id);
        fileService.delete(file);
    }
}