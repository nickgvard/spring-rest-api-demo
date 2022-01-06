package springrestapidemo.restcontroller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springrestapidemo.dto.FileDto;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.service.FileService;
import springrestapidemo.service.amazon.AmazonS3FileService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class FileRestControllerV1 {

    private FileService fileService;
    private AmazonS3FileService s3FileService;

    @GetMapping()
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<List<FileDto>> findAll() {
        return ResponseEntity.ok(fileService.findAll()
                .stream()
                .map(FileDto::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<FileDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(FileDto.toDto(fileService.findById(id)));
    }

    @PostMapping()
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<FileDto> save(@RequestPart(value = "file") MultipartFile multipartFile) {
        FileEntity fileEntity = FileDto.toEntity(multipartFile);

        fileEntity = s3FileService.uploadFileToAmazon(fileEntity, multipartFile);

        FileEntity file = fileService.save(fileEntity);
        return ResponseEntity.ok(FileDto.toDto(file));
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<FileDto> update(@RequestBody FileDto fileDto, @PathVariable Long id) {
        FileEntity file = fileService.findById(id);
        file.setName(fileDto.getName());
        file.setLocation(fileDto.getLocation());
        return ResponseEntity.ok(FileDto.toDto(fileService.update(file)));
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        FileEntity file = fileService.findById(id);

        s3FileService.removeFileFromAmazon(file);

        fileService.delete(file);
    }
}