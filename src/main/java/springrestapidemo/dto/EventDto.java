package springrestapidemo.dto;

import springrestapidemo.entity.FileEntity;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */
public class EventDto {

    private EventDto() {}

    public static FileEntity toEntity(FileDto fileDto) {
        return null;
    }

    public static FileDto toDto(FileEntity fileEntity) {
        return null;
    }
}