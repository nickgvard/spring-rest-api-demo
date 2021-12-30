package springrestapidemo.dto;

import lombok.Builder;
import lombok.Data;
import springrestapidemo.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Data
@Builder
public class UserDto {

    private Long id;
    private String name;
    private List<EventDto> events;

    public static UserEntity toEntity(UserDto userDto) {
        return UserEntity
                .builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .eventEntities(null == userDto.getEvents() ? null : userDto
                        .getEvents()
                        .stream()
                        .map(EventDto::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public static UserDto toDto(UserEntity userEntity) {
        return UserDto
                .builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .events(null == userEntity.getEventEntities() ? null : userEntity
                        .getEventEntities()
                        .stream()
                        .map(EventDto::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}