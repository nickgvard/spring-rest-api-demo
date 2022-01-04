package springrestapidemo.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Nikita Gvardeev
 * 04.01.2022
 */

@Data
@Builder
public class AuthenticationRequestDto {

    private String email;
    private String password;
}
