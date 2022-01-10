package springrestapidemo.dto;

import lombok.Builder;
import lombok.Data;
import springrestapidemo.entity.EventEntity;
import springrestapidemo.entity.UserEntity;

import java.util.Objects;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Data
@Builder
public class EventDto {

    private Long id;
    private UserDto user;
    private FileDto file;
    private String description;

    public static EventEntity toEntity(EventDto eventDto) {
        return EventEntity
                .builder()
                .id(eventDto.getId())
                .userEntity(Objects.isNull(eventDto.getUser()) ? null : UserEntity
                        .builder()
                        .id(eventDto.getUser().getId())
                        .build())
                .fileEntity(Objects.isNull(eventDto.getFile()) ? null : FileDto.toEntity(eventDto.getFile()))
                .description(eventDto.getDescription())
                .build();
    }

    public static EventDto toDto(EventEntity eventEntity) {
        return EventDto
                .builder()
                .id(eventEntity.getId())
                .user(Objects.isNull(eventEntity.getUserEntity()) ? null : UserDto
                        .builder()
                        .id(eventEntity.getUserEntity().getId())
                        .build())
                .file(Objects.isNull(eventEntity.getFileEntity()) ? null : FileDto.toDto(eventEntity.getFileEntity()))
                .description(eventEntity.getDescription())
                .build();
    }
}