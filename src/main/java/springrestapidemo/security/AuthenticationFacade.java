package springrestapidemo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Nikita Gvardeev
 * 06.01.2022
 */

@Component
public class AuthenticationFacade {

    public Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
