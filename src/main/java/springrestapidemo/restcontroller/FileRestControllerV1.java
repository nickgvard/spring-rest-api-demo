package springrestapidemo.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springrestapidemo.dto.FileDto;
import springrestapidemo.service.FileService;

import java.util.List;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileRestControllerV1 {

    private final FileService fileService;

    @GetMapping()
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<List<FileDto>> findAll() {
        return ResponseEntity.ok(fileService.findAll());
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<FileDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(fileService.findById(id));
    }

    @PostMapping()
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<FileDto> save(@RequestPart(value = "file") MultipartFile multipartFile) {
        return ResponseEntity.ok(fileService.save(multipartFile));
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<FileDto> update(@RequestBody FileDto fileDto, @PathVariable Long id) {
        return ResponseEntity.ok(fileService.update(fileDto, id));
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        fileService.delete(id);
    }
}