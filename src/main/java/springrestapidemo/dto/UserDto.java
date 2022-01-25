package springrestapidemo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Status status;
    private List<RoleDto> rolesDto;
    private List<EventDto> eventsDto;

    public static UserEntity toEntity(UserDto userDto) {
        return UserEntity
                .builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .status(userDto.getStatus())
                .roles(Objects.isNull(userDto.getRolesDto()) ? null : userDto
                        .getRolesDto()
                        .stream()
                        .map(RoleDto::toEntity)
                        .collect(Collectors.toList()))
                .eventEntities(Objects.isNull(userDto.getEventsDto()) ? null : userDto
                        .getEventsDto()
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
                .rolesDto(Objects.isNull(userEntity.getRoles()) ? null : userEntity
                        .getRoles()
                        .stream()
                        .map(RoleDto::toDto)
                        .collect(Collectors.toList()))
                .eventsDto(Objects.isNull(userEntity.getEventEntities()) ? null : userEntity
                        .getEventEntities()
                        .stream()
                        .map(EventDto::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}