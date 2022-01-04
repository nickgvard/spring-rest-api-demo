package springrestapidemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Nikita Gvardeev
 * 04.01.2022
 */

@AllArgsConstructor
@Getter
public enum Permission {

    FILES_READ("files:read"),
    FILES_WRITE("files:write"),
    USERS_READ("users:read"),
    USERS_WRITE("users:write"),
    EVENTS_READ("events:read"),
    EVENTS_WRITE("events:write");

    private final String permission;
}
