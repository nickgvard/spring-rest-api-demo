package springrestapidemo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Data;
import springrestapidemo.entity.RoleEntity;

/**
 * @author Nikita Gvardeev
 * 04.01.2022
 */

@Data
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class RoleDto {

    private Long id;
    private String name;

    public static RoleEntity toEntity(RoleDto roleDto) {
        return RoleEntity
                .builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .build();
    }

    public static RoleDto toDto(RoleEntity roleEntity) {
        return RoleDto
                .builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .build();

    }
}
