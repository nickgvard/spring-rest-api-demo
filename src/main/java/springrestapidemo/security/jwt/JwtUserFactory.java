package springrestapidemo.security.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import springrestapidemo.entity.RoleEntity;
import springrestapidemo.entity.Status;
import springrestapidemo.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 04.01.2022
 */
public final class JwtUserFactory {

    public static JwtUser jwtUser(UserEntity userEntity) {
        return JwtUser.builder()
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .enabled(userEntity.getStatus().equals(Status.ACTIVE))
                .authorities(grantedAuthorities(new ArrayList<>(userEntity.getRoles())))
                .build();
    }

    public static List<SimpleGrantedAuthority> grantedAuthorities(List<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
