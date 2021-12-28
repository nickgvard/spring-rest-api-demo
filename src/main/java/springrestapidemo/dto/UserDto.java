package springrestapidemo.dto;

import springrestapidemo.entity.UserEntity;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */
public class UserDto {

    private UserDto() {}

    public static UserEntity toEntity(UserDto userDto) {
        return null;
    }

    public static UserDto toDto(UserEntity userEntity) {
        return null;
    }
}