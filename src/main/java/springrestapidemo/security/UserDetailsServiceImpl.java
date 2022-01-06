package springrestapidemo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.security.jwt.JwtUserFactory;
import springrestapidemo.service.UserService;

/**
 * @author Nikita Gvardeev
 * 04.01.2022
 */

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException("User with email: " + email + " not found");

        return JwtUserFactory.jwtUser(userEntity);
    }
}
