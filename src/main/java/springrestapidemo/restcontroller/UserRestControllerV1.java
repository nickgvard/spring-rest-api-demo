package springrestapidemo.restcontroller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springrestapidemo.dto.UserDto;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserRestControllerV1 {

    private UserService userService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public List<UserDto> findAll() {
        return userService.findAll()
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public UserDto findById(@PathVariable Long id) {
        return UserDto.toDto(userService.findById(id));
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(@RequestBody UserDto userDto) {
        UserEntity user = userService.save(UserDto.toEntity(userDto));
        return UserDto.toDto(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@RequestBody UserDto userDto, @PathVariable Long id) {
        UserEntity user = userService.findById(id);
        user.setEmail(userDto.getEmail());
        return UserDto.toDto(userService.update(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        UserEntity userEntity = userService.findById(id);
        userService.delete(userEntity);
    }
}