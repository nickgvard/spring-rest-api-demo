package springrestapidemo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class EventDto {

    private Long id;
    private UserDto userDto;
    private FileDto fileDto;
    private String description;

    public static EventEntity toEntity(EventDto eventDto) {
        return EventEntity
                .builder()
                .id(eventDto.getId())
                .userEntity(Objects.isNull(eventDto.getUserDto()) ? null : UserEntity
                        .builder()
                        .id(eventDto.getUserDto().getId())
                        .build())
                .fileEntity(Objects.isNull(eventDto.getFileDto()) ? null : FileDto.toEntity(eventDto.getFileDto()))
                .description(eventDto.getDescription())
                .build();
    }

    public static EventDto toDto(EventEntity eventEntity) {
        return EventDto
                .builder()
                .id(eventEntity.getId())
                .userDto(Objects.isNull(eventEntity.getUserEntity()) ? null : UserDto
                        .builder()
                        .id(eventEntity.getUserEntity().getId())
                        .build())
                .fileDto(Objects.isNull(eventEntity.getFileEntity()) ? null : FileDto.toDto(eventEntity.getFileEntity()))
                .description(eventEntity.getDescription())
                .build();
    }
}