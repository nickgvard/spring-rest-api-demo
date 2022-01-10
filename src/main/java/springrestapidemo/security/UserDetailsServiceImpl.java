package springrestapidemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springrestapidemo.dto.UserDto;
import springrestapidemo.security.jwt.JwtUserFactory;
import springrestapidemo.service.UserService;

import java.util.Objects;

/**
 * @author Nikita Gvardeev
 * 04.01.2022
 */

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userDto = userService.findByEmail(email);

        if (Objects.isNull(userDto))
            throw new UsernameNotFoundException("User with email: " + email + " not found");

        return JwtUserFactory.jwtUser(userDto);
    }
}
