package springrestapidemo.dto;

import lombok.Builder;
import lombok.Data;
import springrestapidemo.entity.Status;
import springrestapidemo.entity.UserEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Data
@Builder
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Status status;
    private List<RoleDto> roles;
    private List<EventDto> events;

    public static UserEntity toEntity(UserDto userDto) {
        return UserEntity
                .builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .status(userDto.getStatus())
                .roles(Objects.isNull(userDto.getRoles()) ? null : userDto
                        .getRoles()
                        .stream()
                        .map(RoleDto::toEntity)
                        .collect(Collectors.toList()))
                .eventEntities(Objects.isNull(userDto.getEvents()) ? null : userDto
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
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .password(userEntity.getPassword())
                .status(userEntity.getStatus())
                .roles(Objects.isNull(userEntity.getRoles()) ? null : userEntity
                        .getRoles()
                        .stream()
                        .map(RoleDto::toDto)
                        .collect(Collectors.toList()))
                .events(Objects.isNull(userEntity.getEventEntities()) ? null : userEntity
                        .getEventEntities()
                        .stream()
                        .map(EventDto::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}