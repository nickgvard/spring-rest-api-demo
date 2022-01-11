package springrestapidemo.security.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import springrestapidemo.dto.RoleDto;
import springrestapidemo.dto.UserDto;
import springrestapidemo.entity.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 04.01.2022
 */
public final class JwtUserFactory {

    public static JwtUser jwtUser(UserDto userDto) {
        return JwtUser.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .enabled(userDto.getStatus().equals(Status.ACTIVE))
                .authorities(grantedAuthorities(new ArrayList<>(userDto.getRolesDto())))
                .build();
    }

    public static List<SimpleGrantedAuthority> grantedAuthorities(List<RoleDto> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
