package springrestapidemo.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import springrestapidemo.entity.FileEntity;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Data
@Builder
public class FileDto {

    private Long id;
    private String name;
    private String location;

    public static FileEntity toEntity(FileDto fileDto) {
        return FileEntity
                .builder()
                .id(fileDto.getId())
                .name(fileDto.getName())
                .location(fileDto.getLocation())
                .build();
    }

    public static FileEntity toEntity(MultipartFile multipartFile) {
        return FileEntity
                .builder()
                .name(multipartFile.getOriginalFilename())
                .build();
    }

    public static FileDto toDto(FileEntity fileEntity) {
        return FileDto
                .builder()
                .id(fileEntity.getId())
                .name(fileEntity.getName())
                .location(fileEntity.getLocation())
                .build();
    }

    public static FileDto toDto(MultipartFile multipartFile) {
        return FileDto
                .builder()
                .name(multipartFile.getOriginalFilename())
                .build();
    }
}